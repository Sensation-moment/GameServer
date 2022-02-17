package com.fattyCorps.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.mapper.AccountMapper;
import com.fattyCorps.obj.db.Account;
import com.fattyCorps.obj.msg.HttpStatus;
import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.server.SLoginMsg;
import com.fattyCorps.obj.msg.server.SRegMsg;
import com.fattyCorps.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登录(注册)业务层
 */
@Service
public class LoginService {
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 用户注册
     *
     * @param account 用户名
     * @param password 登录密码
     * @return
     */
    public SRet reg(String account, String password) {
        // 用户名格式错误
        if (!checkAccount(account)) {
            return SRet.error(HttpStatus.REG_ACCOUNT_FORM_ERROR.getCode(), HttpStatus.REG_ACCOUNT_FORM_ERROR.getMsg());
        }

        // 密码格式错误
        if (!checkPassword(password)) {
            return SRet.error(HttpStatus.REG_PASSWORD_FORM_ERROR.getCode(), HttpStatus.REG_PASSWORD_FORM_ERROR.getMsg());
        }

        // 对密码进行加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 生成并插入一条新的结果
        Account accountObj = new Account();
        accountObj.setAccount(account);
        accountObj.setPassword(md5Password);

        try {
            int insert = accountMapper.insert(accountObj);
        }catch (Exception e) {
            // 账号重名
            return SRet.error(HttpStatus.REG_DUPLICATE_ACCOUNT_ERROR.getCode(), HttpStatus.REG_DUPLICATE_ACCOUNT_ERROR.getMsg());
        }

        return SRet.success();
    }

    /**
     * 用户登录
     *
     * @param account 用户名
     * @param password 登录密码
     * @return
     */
    public SRet login(String account, String password) {
        SLoginMsg sLoginMsg = new SLoginMsg();

        QueryWrapper<Account> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("account", account);
        Account accountObj = accountMapper.selectOne(objectQueryWrapper);

        if (accountObj == null) {
            // 没有该账号
            return SRet.error(HttpStatus.LOGIN_FIND_ACCOUNT_ERROR.getCode(), HttpStatus.LOGIN_FIND_ACCOUNT_ERROR.getMsg());
        }

        // 对密码进行加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (md5Password.equals(accountObj.getPassword())) {
            String token = JwtUtil.createAccToken(accountObj.getId());
            sLoginMsg.setToken(token);
            return SRet.success(sLoginMsg);
        }

        // 密码错误
        return SRet.error(HttpStatus.LOGIN_PASSWORD_ERROR.getCode(), HttpStatus.LOGIN_PASSWORD_ERROR.getMsg());
    }

    /**
     * 校验创建事件页面账号格式(使用正则)
     *
     * @param accountNumber
     * @return
     */
    private boolean checkAccount(String accountNumber) {
        String valicateAccount="^[\\w@\\$\\^!~,.\\*]{5,20}+$";
        Pattern pattern = Pattern.compile(valicateAccount);
        Matcher matcher = pattern.matcher(accountNumber);
        boolean matches = matcher.matches();
        if(matches) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 密码校验
     *
     * @param passWord
     * @return
     */
    private boolean checkPassword(String passWord) {
        String valicatePassword="^[\\w@\\$\\^!~,.\\*]{8,16}+$";
        Pattern pattern = Pattern.compile(valicatePassword);
        Matcher matcher = pattern.matcher(passWord);
        boolean matches = matcher.matches();
        if(matches) {
            return true;
        }else {
            return false;
        }
    }
}
