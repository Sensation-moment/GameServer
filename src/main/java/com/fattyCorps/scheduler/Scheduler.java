package com.fattyCorps.scheduler;

import com.fattyCorps.cache.PlayerCache;
import com.fattyCorps.obj.cache.PlayerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务
 */
@Component
@Slf4j
public class Scheduler {
    @Autowired
    PlayerCache playerCache;
    /**
     * 2分钟没有收到心跳就认为掉线了
     */
    private final Integer LOGOUT_TIME = 2 * 60 * 1000;

    /**
     * 定时检测用户心跳 5分钟
     */
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void heartBeatTask() {
        long curTime = System.currentTimeMillis();
        ConcurrentHashMap<Integer, PlayerInfo> playerInfos = playerCache.getPlayerInfos();
        for (PlayerInfo playerInfo : playerInfos.values()) {
            if (playerInfo.getLastHeartBeatTime() + LOGOUT_TIME < curTime) {
                log.info("用户:" + playerInfo.getBaseProp().getId() + "下线了");
                playerCache.saveOne(playerInfo);
                // 移除
                playerInfos.remove(playerInfo.getBaseProp().getId());
            }
        }
    }

    /**
     * 每个小时保存一次
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void saveAll() {
        playerCache.saveAll();
    }
}
