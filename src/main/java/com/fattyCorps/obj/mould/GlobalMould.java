package com.fattyCorps.obj.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 全局模板
 */
@Data
@TableName("config_global")
public class GlobalMould {
    private Integer id;
    private String name;
    private String value;
    private String des;
}
