package com.fattyCorps.config.gameConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.mapper.GlobalMouldMapper;
import com.fattyCorps.obj.mould.GlobalMould;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 全局配置类
 */
@Component
@Slf4j
public class GlobalConfig implements CommandLineRunner {
    @Autowired
    GlobalMouldMapper globalMouldMapper;
    /**
     * 全局配置Map
     */
    private HashMap<String, String> globalConfigMap = new HashMap<>();

    public static final String STAR_LV = "star_lv";
    public static final String MAX_STAR = "max_star";
    public static final String ONE_LOTTERY_COIN = "one_lottery_coin";
    public static final String TEN_LOTTERY_COIN = "ten_lottery_coin";
    public static final String FREE_LOTTERY_TIME = "free_lottery_time";
    public static final String ONE_HERO_FRAGMENT_NUM = "one_hero_fragment_num";
    public static final String EXP_ITEM_TYPE_ID = "exp_item_type_id";
    public static final String STAR_STONE_ITEM_TYPE_ID = "star_stone_item_type_id";
    public static final String LOTTERY_ITEM_TYPE_ID = "lottery_item_type_id";

    /**
     * 部分初始化信息
     */
    public static final String PLAYER_INIT_ITEMS = "player_init_items";
    public static final String PLAYER_INIT_HEAD = "player_init_head";
    public static final String PLAYER_INIT_LV = "player_init_lv";
    public static final String PLAYER_INIT_COIN = "player_init_coin";
    public static final String PLAYER_INIT_GUANQIA = "player_init_guanqia";

    /**
     * 获取配置对应名字
     *
     * @param name
     * @return
     */
    public String getStringValue(String name) {
        return globalConfigMap.get(name);
    }

    /**
     * 获取配置对应的实际数值
     *
     * @param name
     * @return
     */
    public Integer getIntValue(String name) {
        return Integer.valueOf(globalConfigMap.get(name));
    }

    /**
     * 最开始的初始化
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //// 打印到控制台
        //QueryWrapper<GlobalMould> wrapper = new QueryWrapper<>();
        //List<GlobalMould> globalMoulds = globalMouldMapper.selectList(wrapper);
        //for (int i = 0; i < globalMoulds.size(); i++) {
        //    GlobalMould globalMould = globalMoulds.get(i);
        //    globalConfigMap.put(globalMould.getName(), globalMould.getValue());
        //}
    }
}
