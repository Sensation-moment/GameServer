package com.fattyCorps.controller;

import com.fattyCorps.mapper.AccountMapper;
import com.fattyCorps.obj.db.Account;
import com.fattyCorps.obj.msg.client.CLoginMsg;
import com.fattyCorps.obj.msg.server.SLoginMsg;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    AccountMapper accountMapper;

    @GetMapping("/test")
    @ApiOperation("测试接口")
    public String test(){
        Account account = accountMapper.selectById(1);
        String ret = "hello test ok：" + account.getAccount();
        log.info(ret);
        return ret;
    }

    @GetMapping("/add")
    public String add() {
        Account account = new Account();
        account.setAccount("zhangsan");
        account.setPassword("zsmima123");
        accountMapper.insert(account);
        return "";
    }

    @GetMapping("/modify")
    public String modify(Integer id, String newPwd) {
        Account account = accountMapper.selectById(id);
        account.setPassword(newPwd);
        accountMapper.updateById(account);
        return "";
    }

    @GetMapping("/select")
    public String select(Integer id) {
        Account account = accountMapper.selectById(id);
        return account.getAccount();
    }
}
