package com.fattyCorps.obj.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 道具模板
 */
@Data
@TableName("config_item")
public class ItemMould {
    private Integer id;
    private String name;
    private Integer type;
    private String prop;
}
