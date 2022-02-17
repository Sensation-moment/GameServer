package com.fattyCorps.obj.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
@TableName("account")
public class Account extends BaseDBTable{
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
