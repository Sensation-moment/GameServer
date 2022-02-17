package com.fattyCorps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fattyCorps.obj.db.Item;
import com.fattyCorps.obj.mould.ItemMould;
import org.springframework.stereotype.Repository;

/**
 * 道具模板映射接口
 */
@Repository
public interface ItemMouldMapper extends BaseMapper<ItemMould> {
}
