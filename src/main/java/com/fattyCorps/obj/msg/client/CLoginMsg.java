package com.fattyCorps.obj.msg.client;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("登录消息结构")
public class CLoginMsg {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
}
