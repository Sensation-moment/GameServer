package com.fattyCorps.controller;

import com.fattyCorps.obj.db.Hero;
import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.client.hero.CCreateHeroMsg;
import com.fattyCorps.obj.msg.client.hero.CHeroLvUpMsg;
import com.fattyCorps.obj.msg.client.hero.CHeroStarUpMsg;
import com.fattyCorps.service.HeroService;
import com.fattyCorps.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 英雄控制器
 */
@RestController
public class HeroController {
    @Autowired
    private HeroService heroService;

    /**
     * 创建英雄
     *
     * @param request
     * @param cCreateHeroMsg
     * @return
     */
    @PostMapping("/create")
    public SRet create(HttpServletRequest request, @RequestBody CCreateHeroMsg cCreateHeroMsg) {
        // 解析出playerID
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        // 通过playerID和英雄的类型ID和service层实现对应逻辑
        heroService.createHero(playerId, cCreateHeroMsg.getHeroTypeId());
        return SRet.success();
    }

    /**
     * 获取英雄列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getHeroList")
    public SRet getHeroList(HttpServletRequest request) {
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        SRet heroList = heroService.getHeroList(playerId);
        return heroList;
    }

    /**
     * 英雄升级
     *
     * @param request
     * @param cHeroLvUpMsg
     * @return
     */
    @PostMapping("/lvUp")
    public SRet lvUp(HttpServletRequest request, @RequestBody CHeroLvUpMsg cHeroLvUpMsg) {
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        SRet<Hero> heroSRet = heroService.lvUp(playerId, cHeroLvUpMsg.getHeroId());
        return heroSRet;
    }

    /**
     * 英雄升星
     *
     * @param request
     * @param cHeroStarUpMsg
     * @return
     */
    @PostMapping("/starUp")
    public SRet starUp(HttpServletRequest request, @RequestBody CHeroStarUpMsg cHeroStarUpMsg) {
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        SRet<Hero> heroSRet = heroService.starUp(playerId, cHeroStarUpMsg.getHeroId(), cHeroStarUpMsg.getConsumeHeroId1(), cHeroStarUpMsg.getConsumeHeroId2(), cHeroStarUpMsg.getConsumeHeroId3());
        return heroSRet;
    }
}
