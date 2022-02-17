package com.fattyCorps.config.gameConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.mapper.HeroMapper;
import com.fattyCorps.mapper.HeroMouldMapper;
import com.fattyCorps.obj.mould.HeroMould;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 英雄配置类
 */
@Component
@Slf4j
public class HeroConfig implements CommandLineRunner {
    @Autowired
    private HeroMouldMapper heroMouldMapper;

    private List<HeroMould> heroMouldList = new ArrayList<>();

    /**
     * 外部访问接口
     *
     * @return
     */
    public List<HeroMould> getHeroMouldList() {
        return heroMouldList;
    }

    public HeroMould getHeroMould(Integer typeId) {
        for (int i = 0; i < heroMouldList.size(); i++) {
            HeroMould heroMould = heroMouldList.get(i);
            if (heroMould.getId().equals(typeId)) {
                return heroMould;
            }
        }
        return null;
    }

    /**
     * 最开始的初始化
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //// 加载配置
        //// 根据需求增加配置到heroMouldList
        //QueryWrapper<HeroMould> wrapper = new QueryWrapper<>();
        //List<HeroMould> heroMoulds = heroMouldMapper.selectList(wrapper);
        //this.heroMouldList.addAll(heroMoulds);
    }
}


