package com.fattyCorps.controller;

import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.client.CLoginMsg;
import com.fattyCorps.obj.msg.client.CRegMsg;
import com.fattyCorps.obj.msg.server.SLoginMsg;
import com.fattyCorps.obj.msg.server.SRegMsg;
import com.fattyCorps.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户注册
     *
     * @param cRegMsg
     * @return
     */
    @PostMapping("/reg")
    public SRet reg(@RequestBody CRegMsg cRegMsg) {
        SRet reg = loginService.reg(cRegMsg.getAccount(), cRegMsg.getPassword());
        return reg;
    }

    /**
     * 用户登录
     *
     * @param cLoginMsg
     * @return
     */
    @PostMapping("/login")
    public SRet login(@RequestBody CLoginMsg cLoginMsg) {
        SRet login = loginService.login(cLoginMsg.getAccount(), cLoginMsg.getPassword());
        return login;
    }
}
