package com.fattyCorps.obj.msg.server.player;

import lombok.Data;

@Data
public class SGetInfo {
    private String nickname;
    private Integer lv;
    private Integer head;
    private Integer coin;
    private Integer guanqia;
    private String newToken;
}
