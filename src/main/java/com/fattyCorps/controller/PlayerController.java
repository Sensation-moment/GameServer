package com.fattyCorps.controller;

import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.service.PlayerService;
import com.fattyCorps.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 玩家(角色)控制器
 */
@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    /**
     * 获取角色信息
     *
     * @param request
     * @return
     */
    @PostMapping("/getInfo")
    public SRet getInfo(HttpServletRequest request){
        Integer accountId = ServletUtils.getAccIdByRequest(request);
        // 获取到了账号ID
        SRet info = playerService.getInfo(accountId);
        return info;
    }

    /**
     * 心跳功能
     *
     * @param request
     * @return
     */
    @PostMapping("/heartBeat")
    public SRet heartBeat(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        playerService.procHeartBeat(playerId);
        return SRet.success();
    }
}
