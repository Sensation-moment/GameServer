package com.fattyCorps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fattyCorps.obj.db.Account;
import org.springframework.stereotype.Repository;

/**
 * 用户映射接口
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {
}
