package com.fattyCorps.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.cache.PlayerCache;
import com.fattyCorps.config.gameConfig.GlobalConfig;
import com.fattyCorps.config.gameConfig.HeroConfig;
import com.fattyCorps.exception.gameException.hero.*;
import com.fattyCorps.mapper.HeroMapper;
import com.fattyCorps.obj.cache.PlayerInfo;
import com.fattyCorps.obj.db.Hero;
import com.fattyCorps.obj.mould.HeroMould;
import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.server.hero.SGetHeroListMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 英雄业务层
 */
@Service
@Slf4j
public class HeroService {
    @Autowired
    private PlayerCache playerCache;
    @Autowired
    private HeroMapper heroMapper;
    @Autowired
    private HeroConfig heroConfig;
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private ItemService itemService;

    /**
     * 创建英雄
     *
     * @param playerId 玩家ID
     * @param typeId   模板ID
     */
    public void createHero(Integer playerId, Integer typeId) {
        HeroMould heroMould = heroConfig.getHeroMould(typeId);
        if (heroMould == null) {
            throw new HeroMouldIdErrorException();
        }

        Hero hero = new Hero();
        hero.setPlayerId(playerId);
        hero.setHeroName(heroMould.getName());
        hero.setAtt(heroMould.getInitAtt());
        hero.setDef(heroMould.getInitDef());
        hero.setMaxHp(heroMould.getInitMaxHp());
        hero.setLv(1);
        hero.setStar(1);
        hero.setTypeId(typeId);

        // 插入数据库
        try {
            heroMapper.insert(hero);
        } catch (Exception e) {
            log.error("createHero error: playerId=" + playerId + " typeId=" + typeId);
            throw new HeroCreateErrorException();
        }

        // 增加数据到内存
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        playerInfo.getHeroes().add(hero);
    }

    /**
     * 从数据库中加载数据到内存
     *
     * @param playerInfo
     */
    public void initCache(PlayerInfo playerInfo) {
        // 访问数据库，获取用户英雄信息
        QueryWrapper<Hero> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", playerInfo.getBaseProp().getId());
        List<Hero> heroes = heroMapper.selectList(wrapper);

        // 将英雄数据放入到缓存中
        playerInfo.setHeroes(heroes);
    }

    /**
     * 获取英雄列表
     *
     * @param playerId
     * @return
     */
    public SRet getHeroList(Integer playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        SGetHeroListMsg sGetHeroListMsg = new SGetHeroListMsg();
        sGetHeroListMsg.setHeroList(heroes);
        return SRet.success(sGetHeroListMsg);
    }

    /**
     * 升级
     *
     * @param playerId
     * @param heroId
     * @return
     */
    public SRet<Hero> lvUp(Integer playerId, Integer heroId) {
        Hero hero = getHero(playerId, heroId);

        // 每星提升20级等级上限
        Integer maxLv = hero.getStar() * globalConfig.getIntValue(GlobalConfig.STAR_LV);
        if (hero.getLv() >= maxLv) {
            throw new HeroLvMaxErrorException();
        }

        // 判断道具是否足够
        Integer needExpItemNum = hero.getLv() * 100;
        Integer expItemTypeId = globalConfig.getIntValue(GlobalConfig.EXP_ITEM_TYPE_ID);
        boolean b = itemService.delItemByTypeId(playerId, expItemTypeId, needExpItemNum);
        if (!b) {
            throw new HeroExpNotEnoughErrorException();
        }

        // 先初始英雄模板
        HeroMould heroMould = heroConfig.getHeroMould(hero.getTypeId());

        // 升级属性
        hero.setLv(hero.getLv() + 1);
        hero.setAtt(hero.getAtt() + heroMould.getLvUpAtt());
        hero.setDef(hero.getDef() + heroMould.getLvUpDef());
        hero.setMaxHp(hero.getMaxHp() + heroMould.getLvUpMaxHp());

        return SRet.success(hero);
    }

    /**
     * 英雄升星
     *
     * @param playerId
     * @param heroId
     * @param consumeHeroId1
     * @param consumeHeroId2
     * @param consumeHeroId3
     * @return
     */
    public SRet<Hero> starUp(Integer playerId, Integer heroId, Integer consumeHeroId1, Integer consumeHeroId2, Integer consumeHeroId3) {
        Hero hero = getHero(playerId, heroId);

        Integer maxStar = globalConfig.getIntValue(GlobalConfig.MAX_STAR);
        if (hero.getStar() >= maxStar) {
            throw new HeroStarMaxErrorException();
        }

        // 判断道具是否够
        Integer needStarStoneItemNum = hero.getStar() * 10;
        Integer starStoneItemTypeId = globalConfig.getIntValue(GlobalConfig.STAR_STONE_ITEM_TYPE_ID);
        boolean b = itemService.delItemByTypeId(playerId, starStoneItemTypeId, needStarStoneItemNum);
        if (!b) {
            throw new HeroStarStoneNotEnoughErrorException();
        }

        // 判断英雄是否足够，要消耗一个同星本体，以及两个同星其他英雄
        Hero heroConsume1 = getHero(playerId, consumeHeroId1);
        Hero heroConsume2 = getHero(playerId, consumeHeroId2);
        Hero heroConsume3 = getHero(playerId, consumeHeroId3);
        if (heroConsume1.getTypeId() != hero.getTypeId() || heroConsume1.getStar() != hero.getStar() ||
                heroConsume2.getStar() != hero.getStar() || heroConsume3.getStar() != hero.getStar()) {
            throw new HeroStarUpMaterialErrorException();
        }

        // 删除数据库英雄信息
        try {
            heroMapper.deleteById(consumeHeroId1);
            heroMapper.deleteById(consumeHeroId2);
            heroMapper.deleteById(consumeHeroId3);
        } catch (Exception e) {
            log.error("starUp error: playerId=" + playerId + " heroId=" + heroId + " consumeHeroId1=" + consumeHeroId1 + " consumeHeroId2=" + consumeHeroId2 + " consumeHeroId3=" + consumeHeroId3);
            throw new HeroStarUpDelErrorException();
        }

        // 删除内存英雄信息
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        heroes.remove(heroConsume1);
        heroes.remove(heroConsume2);
        heroes.remove(heroConsume3);

        // 修改内存，给英雄升星
        hero.setStar(hero.getStar() + 1);
        return SRet.success(hero);
    }

    /**
     * 根据玩家ID和英雄ID获取英雄
     *
     * @param playerId
     * @param heroId
     * @return
     */
    private Hero getHero(Integer playerId, Integer heroId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            if (hero.getId().equals(heroId)) {
                return hero;
            }
        }
        throw new HeroIdErrorException();
    }
}
