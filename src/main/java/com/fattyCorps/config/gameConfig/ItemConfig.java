package com.fattyCorps.config.gameConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.mapper.ItemMouldMapper;
import com.fattyCorps.obj.mould.ItemMould;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 道具配置类
 */
@Component
@Slf4j
public class ItemConfig implements CommandLineRunner {

    // 普通
    public final static int ITEM_TYPE_HERO_NOR = 1;
    // 英雄碎片
    public final static int ITEM_TYPE_HERO_FRAGMENT = 2;
    // 元宝
    public final static int ITEM_TYPE_HERO_COIN = 3;
    // 挂机奖励
    public final static int ITEM_TYPE_HERO_IDLE_PRICE = 4;

    private List<ItemMould> itemMouldList = new ArrayList<>();

    /**
     * 通过道具模板id获取模板信息
     *
     * @param typeId
     * @return
     */
    public ItemMould getItemMould(Integer typeId) {
        for (int i = 0; i < itemMouldList.size(); i++) {
            ItemMould itemMould = itemMouldList.get(i);
            if (itemMould.getId().equals(typeId)) {
                return itemMould;
            }
        }

        log.debug("未找到具模板id为" + typeId + "的道具");
        return null;
    }

    @Autowired
    ItemMouldMapper itemMouldMapper;

    @Override
    public void run(String... args) throws Exception {
//        // 加载配置
////        根据需求增加配置到itemMouldList
//        QueryWrapper<ItemMould> wrapper = new QueryWrapper<>();
//        List<ItemMould> itemMoulds = itemMouldMapper.selectList(wrapper);
//        this.itemMouldList.addAll(itemMoulds);
    }
}
