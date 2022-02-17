package com.fattyCorps.obj.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 英雄模板
 */
@Data
@TableName("config_hero")
public class HeroMould {
    private Integer id;
    private String name;
    private Integer initAtt;
    private Integer initDef;
    private Integer initMaxHp;
    private Integer lvUpAtt;
    private Integer lvUpDef;
    private Integer lvUpMaxHp;
}
