package com.fattyCorps.obj.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 玩家实体类
 */
@Data
@TableName("player")
public class Player extends BaseDBTable{
    private Integer accountId;
    private String nickname;
    private Integer lv;
    private Integer head;
    private Integer coin;
    private Integer guanqia;
}
