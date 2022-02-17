package com.fattyCorps.obj.msg.server;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("登录消息返回结构")
public class SLoginMsg {
    private String token;
}
