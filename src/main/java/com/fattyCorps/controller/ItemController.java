package com.fattyCorps.controller;

import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.client.item.CAddItemMsg;
import com.fattyCorps.obj.msg.client.item.CUseItemMsg;
import com.fattyCorps.service.ItemService;
import com.fattyCorps.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 道具(背包)控制器
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    /**
     * 增加道具
     *
     * @param request
     * @param cAddItemMsg
     * @return
     */
    @PostMapping("/addItem")
    public SRet addItem(HttpServletRequest request, @RequestBody CAddItemMsg cAddItemMsg) {
        // 解析出playerID
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        // 传入角色ID，道具种类ID，数量
        itemService.addItem(playerId, cAddItemMsg.getItemTypeId(), cAddItemMsg.getNum());
        return SRet.success();
    }

    /**
     * 获取道具信息
     *
     * @param request
     * @return
     */
    @PostMapping("/getItemList")
    public SRet getItemList(HttpServletRequest request) {
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        SRet itemList = itemService.getItemList(playerId);
        return itemList;
    }

    /**
     * 使用道具
     *
     * @param request
     * @param cUseItemMsg
     * @return
     */
    @PostMapping("/useItem")
    public SRet useItem(HttpServletRequest request, @RequestBody CUseItemMsg cUseItemMsg) {
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        SRet sRet = itemService.useItem(playerId, cUseItemMsg.getItemId(), cUseItemMsg.getNum());
        return sRet;
    }
}
