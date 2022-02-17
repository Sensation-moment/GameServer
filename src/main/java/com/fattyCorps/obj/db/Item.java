package com.fattyCorps.obj.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 道具实体类
 */
@Data
@TableName("item")
public class Item extends BaseDBTable{
    /**
     * 角色ID
     */
    private Integer playerId;
    /**
     * 类型
     */
    private Integer typeId;
    /**
     * 名字
     */
    private String name;
    /**
     * 数量
     */
    private Integer num;
}