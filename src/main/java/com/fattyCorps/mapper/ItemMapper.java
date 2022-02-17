package com.fattyCorps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fattyCorps.obj.db.Hero;
import com.fattyCorps.obj.db.Item;
import org.springframework.stereotype.Repository;

/**
 * 道具(背包)映射接口
 */
@Repository
public interface ItemMapper extends BaseMapper<Item> {
}
