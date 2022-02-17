package com.fattyCorps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fattyCorps.obj.mould.HeroMould;
import org.springframework.stereotype.Repository;

/**
 * 英雄模板映射接口
 */
@Repository
public interface HeroMouldMapper extends BaseMapper<HeroMould> {
}
