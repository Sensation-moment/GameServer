package com.fattyCorps.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.cache.PlayerCache;
import com.fattyCorps.config.gameConfig.GlobalConfig;
import com.fattyCorps.mapper.PlayerMapper;
import com.fattyCorps.obj.cache.PlayerInfo;
import com.fattyCorps.obj.db.Player;
import com.fattyCorps.obj.msg.HttpStatus;
import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.server.player.SGetInfo;
import com.fattyCorps.utils.DateUtils;
import com.fattyCorps.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * 玩家(角色)业务层
 */
@Service
@Slf4j
public class PlayerService {
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private PlayerCache playerCache;
    @Autowired
    private HeroService heroService;
    @Autowired
    private ItemService itemService;

    /**
     * 获取角色信息
     *
     * @param accountId
     * @return
     */
    public SRet getInfo(Integer accountId) {
        // 查询对应的角色信息
        QueryWrapper<Player> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        Player player = playerMapper.selectOne(wrapper);

        // 如果查不到
        if (player == null) {// 就创建
            // 生成部分初始化数值
            Integer initHead = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_HEAD);
            Integer initLv = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_LV);
            Integer initCoin = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_COIN);
            Integer initGuanqia = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_GUANQIA);

            // 初始化
            player = new Player();
            player.setNickname("");
            player.setHead(initHead);
            player.setLv(initLv);
            player.setCoin(initCoin);
            player.setGuanqia(initGuanqia);
            player.setAccountId(accountId);

            SimpleDateFormat df = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
            String initTimeStr = df.format(Integer.valueOf(0));

            try {
                // 插入一条数据
                playerMapper.insert(player);
            } catch (Exception e) {
                log.error("getInfo error: accountId" + accountId);
                SRet.error(HttpStatus.PLAYER_CREATE_ERROR.getCode(), HttpStatus.PLAYER_CREATE_ERROR.getMsg());
            }
        }

        // 置入缓存
        initCache(player);

        // 创建Token
        String playerToken = JwtUtil.createPlayerToken(player.getId());

        // 向客户端返回角色信息
        SGetInfo sGetInfo = new SGetInfo();
        sGetInfo.setNickname(player.getNickname());
        sGetInfo.setHead(player.getHead());
        sGetInfo.setLv(player.getLv());
        sGetInfo.setCoin(player.getCoin());
        sGetInfo.setGuanqia(player.getGuanqia());
        sGetInfo.setNewToken(playerToken);

        return SRet.success(sGetInfo);
    }

    /**
     * 加载数据库信息到缓存
     */
    private void initCache(Player player) {
        // 加载用户基础信息到缓存
        PlayerInfo playerInfo = playerCache.getPlayerInfo(player.getId(), false);
        if (playerInfo != null) {
            return;
        }

        // 构建一下
        playerInfo = new PlayerInfo();
        playerInfo.setBaseProp(player);

        // 初始化心跳时间
        playerInfo.setLastHeartBeatTime(System.currentTimeMillis());

        // 加载用户英雄信息到缓存
        // 调用英雄模块 HeroService的相关方法去加载
        heroService.initCache(playerInfo);

        // 加载道具信息
        itemService.initCache(playerInfo);

        // 加载其它模块
        playerCache.addPlayerInfo(playerInfo);

        return;
    }

    /**
     * 增加或删除元宝
     *
     * @param playerId
     * @param coin
     * @return
     */
    public boolean addCoin(Integer playerId, Integer coin) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseProp = playerInfo.getBaseProp();
        Integer curCoin = baseProp.getCoin();

        // 判断元宝
        if (coin < 0) {
            // 扣钱
            if (curCoin < -coin) {
                // 元宝不足
                return false;
            }
        } else if (coin == 0) {
            return true;
        }

        Integer newCoin = curCoin + coin;
        baseProp.setCoin(newCoin);

        return true;
    }

    /**
     * 创建角色时初始化用户身上的道具
     *
     * @param player
     */
    public void initPlayerItems(Player player) {
        String initItemsStr = globalConfig.getStringValue(GlobalConfig.PLAYER_INIT_ITEMS);
        // 将道具分割开
        String[] split = initItemsStr.split(",");

        for (int i = 0; i < split.length; i++) {
            Integer itemTypeID = Integer.valueOf(split[i]);
            i = i + 1;
            Integer itemNum = Integer.valueOf(split[i]);

            // 增加道具
            itemService.addItemInDB(player.getId(), itemTypeID, itemNum);
        }
    }

    /**
     * 处理心跳
     *
     * @param playerId
     */
    public void procHeartBeat(Integer playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        // 获取并设置上一次心跳时间为当前这一时刻
        long curTime = System.currentTimeMillis();
        playerInfo.setLastHeartBeatTime(curTime);
    }
}
