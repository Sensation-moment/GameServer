package com.fattyCorps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fattyCorps.obj.db.Hero;
import org.springframework.stereotype.Repository;

/**
 * 英雄映射接口
 */
@Repository
public interface HeroMapper extends BaseMapper<Hero> {
}
