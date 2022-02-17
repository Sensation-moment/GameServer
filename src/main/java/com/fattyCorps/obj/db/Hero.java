package com.fattyCorps.obj.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 英雄实体类
 */
@Data
@TableName("hero")
public class Hero extends BaseDBTable{
    /**
     * 角色ID
     */
    private Integer playerId;
    /**
     * 类型
     */
    private Integer typeId;
    /**
     * 英雄名
     */
    private String heroName;
    /**
     * 攻击力
     */
    private Integer att;
    /**
     * 防御力
     */
    private Integer def;
    /**
     * 生命值
     */
    private Integer maxHp;
    /**
     * 等级
     */
    private Integer lv;
    /**
     * 星级
     */
    private Integer star;
}
