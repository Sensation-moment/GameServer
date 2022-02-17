package com.fattyCorps.obj.cache;

import com.fattyCorps.obj.db.Hero;
import com.fattyCorps.obj.db.Item;
import com.fattyCorps.obj.db.Player;
import lombok.Data;

import java.util.List;

/**
 * 角色信息
 */
@Data
public class PlayerInfo {
    /**
     * 角色基础属性
     */
    private Player baseProp;
    /**
     * 英雄属性
     */
    private List<Hero> heroes;
    /**
     * 上一次心跳检测时间
     */
    private Long lastHeartBeatTime;
    /**
     * 道具属性
     */
    private List<Item> items;
}
