package com.fattyCorps.cache;

import com.fattyCorps.exception.gameException.NotLoginErrorException;
import com.fattyCorps.mapper.HeroMapper;
import com.fattyCorps.mapper.ItemMapper;
import com.fattyCorps.mapper.PlayerMapper;
import com.fattyCorps.obj.cache.PlayerInfo;
import com.fattyCorps.obj.db.Hero;
import com.fattyCorps.obj.db.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 角色缓存类
 */
@Component
public class PlayerCache {
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private HeroMapper heroMapper;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 保证线程安全，将player_id与用户内存属性做映射，所以还要创建PlayerInfo结构
     */
    private ConcurrentHashMap<Integer, PlayerInfo> playerInfos = new ConcurrentHashMap<>();

    public PlayerInfo getPlayerInfo(Integer playerId) {
        return getPlayerInfo(playerId, true);
    }

    public ConcurrentHashMap<Integer, PlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public PlayerInfo getPlayerInfo(Integer playerId, boolean throwException) {
        PlayerInfo playerInfo = playerInfos.get(playerId);
        if (playerInfo == null && throwException) {
            // 请重新登录
            throw new NotLoginErrorException();
        }
        return playerInfo;
    }

    public void addPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfos.put(playerInfo.getBaseProp().getId(), playerInfo);
    }

    /**
     * 保存单个玩家数据
     *
     * @param playerInfo
     */
    public void saveOne(PlayerInfo playerInfo) {
        // 保存用户基础属性
        playerMapper.updateById(playerInfo.getBaseProp());

        // 保存英雄属性
        List<Hero> heroes = playerInfo.getHeroes();
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            heroMapper.updateById(hero);
        }

        // 保存道具信息
        List<Item> items = playerInfo.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            itemMapper.updateById(item);
        }
    }

    /**
     * 保存所有角色数据
     */
    public void saveAll() {
        // 遍历并存储
        for (PlayerInfo playerInfo : playerInfos.values()) {
            saveOne(playerInfo);
        }
    }
}
