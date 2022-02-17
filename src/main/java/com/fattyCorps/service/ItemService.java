package com.fattyCorps.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fattyCorps.cache.PlayerCache;
import com.fattyCorps.config.gameConfig.GlobalConfig;
import com.fattyCorps.config.gameConfig.ItemConfig;
import com.fattyCorps.exception.gameException.item.*;
import com.fattyCorps.mapper.ItemMapper;
import com.fattyCorps.obj.cache.PlayerInfo;
import com.fattyCorps.obj.db.Item;
import com.fattyCorps.obj.mould.ItemMould;
import com.fattyCorps.obj.msg.SRet;
import com.fattyCorps.obj.msg.server.item.SGetItemListMsg;
import com.fattyCorps.obj.msg.server.item.SUseItemMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 道具(背包)业务层
 */
@Service
@Slf4j
public class ItemService {
    @Autowired
    PlayerCache playerCache;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ItemConfig itemConfig;
    @Autowired
    HeroService heroService;
    @Autowired
    PlayerService playerService;
    @Autowired
    GlobalConfig globalConfig;


    /**
     * 从数据库中加载数据到内存
     *
     * @param playerInfo
     */
    public void initCache(PlayerInfo playerInfo) {
        // 访问数据库，获取用户英雄信息
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", playerInfo.getBaseProp().getId());
        List<Item> items = itemMapper.selectList(wrapper);

        // 将英雄数据放入缓存中
        playerInfo.setItems(items);
    }

    /**
     * 获取道具列表
     *
     * @param playerId
     * @return
     */
    public SRet getItemList(Integer playerId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        SGetItemListMsg sGetItemListMsg = new SGetItemListMsg();
        sGetItemListMsg.setItemList(items);
        return SRet.success(sGetItemListMsg);
    }

    /**
     * 使用道具
     *
     * @param playerId
     * @param itemId
     * @param num
     * @return
     */
    public SRet useItem(Integer playerId, Integer itemId, Integer num) {
        if (num <= 0) {
            throw new ItemNumErrorException();
        }

        Item item = getItem(playerId, itemId);
        ItemMould itemMould = itemConfig.getItemMould(item.getTypeId());
        switch (itemMould.getType()) {
            case ItemConfig.ITEM_TYPE_HERO_FRAGMENT: {
                // 英雄碎片
                Integer heroTypeId = Integer.valueOf(itemMould.getProp());
                Integer oneHeroFragmentNum = globalConfig.getIntValue(GlobalConfig.ONE_HERO_FRAGMENT_NUM);
                if (num % oneHeroFragmentNum != 0) {
                    throw new ItemNumErrorException();
                }
                int heroNum = num / oneHeroFragmentNum;

                // 删除道具
                boolean b = delItem(item, num);
                if (b) {
                    // 创建英雄
                    for (int i = 0; i < heroNum; i++) {
                        heroService.createHero(playerId, heroTypeId);
                    }
                } else {
                    // 道具数量不足
                    throw new ItemNotEnoughErrorException();
                }
                break;
            }
            case ItemConfig.ITEM_TYPE_HERO_COIN: {
                // 元宝
                Integer coinNum = Integer.valueOf(itemMould.getProp());
                // 删除道具
                boolean b = delItem(item, num);
                if (b) {
                    playerService.addCoin(playerId, coinNum * num);
                } else {
                    // 道具数量不足
                    throw new ItemNotEnoughErrorException();
                }
                break;
            }
            case ItemConfig.ITEM_TYPE_HERO_IDLE_PRICE: {
                // 挂机奖励
                // 还未实现
                break;
            }
            default:
                // 普通道具
                throw new ItemCanNotUseErrorException();
        }

        // 更新并返回道具最新的数量
        SUseItemMsg sUseItemMsg = new SUseItemMsg();
        sUseItemMsg.setNewNum(item.getNum());
        return SRet.success(sUseItemMsg);
    }

    /**
     * 创建道具
     *
     * @param playerId
     * @param itemTypeId
     * @param num
     */
    public void addItem(Integer playerId, Integer itemTypeId, Integer num) {
        Item item = getItemByTypeId(playerId, itemTypeId);
        // 判空
        if (item == null) {
            // 用户身上没有该typeId的道具
            Item newItem = addItemInDB(playerId, itemTypeId, num);

            // 从内存中读取数据，再去内存中增加
            PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
            playerInfo.getItems().add(newItem);
        } else {
            // 用户身上有该typeId的道具
            Integer newNum = item.getNum() + num;
            item.setNum(newNum);
        }
    }

    /**
     * 在数据库中添加道具
     *
     * @param playerId
     * @param itemTypeId
     * @param num
     * @return
     */
    public Item addItemInDB(Integer playerId, Integer itemTypeId, Integer num) {
        ItemMould itemMould = itemConfig.getItemMould(itemTypeId);
        Item newItem = new Item();
        newItem.setPlayerId(playerId);
        newItem.setTypeId(itemTypeId);
        newItem.setNum(num);
        newItem.setName(itemMould.getName());

        // 先去数据库里添加
        try {
            itemMapper.insert(newItem);
        } catch (Exception e) {
            log.error("createItem error: playerId=" + playerId + " itemTypeId=" + itemTypeId);
            throw new ItemCreateErrorException();
        }
        return newItem;
    }

    /**
     * 根据道具类型ID删除道具
     *
     * @param playerId
     * @param itemTypeId
     * @param delNum
     * @return
     */
    public boolean delItemByTypeId(Integer playerId, Integer itemTypeId, Integer delNum) {
        Item item = getItemByTypeId(playerId, itemTypeId);
        return delItem(item, delNum);
    }


    /**
     * 删除道具
     *
     * @param playerId
     * @param itemId
     * @param delNum
     * @return
     */
    public boolean delItem(Integer playerId, Integer itemId, Integer delNum) {
        Item item = getItem(playerId, itemId);
        return delItem(item, delNum);
    }

    /**
     * 删除道具
     *
     * @param item
     * @param delNum
     * @return
     */
    public boolean delItem(Item item, Integer delNum) {
        if (item.getNum() < delNum) {
            // 道具数量不足
            return false;
        } else {// 道具数量足够
            int newNum = item.getNum() - delNum;
            item.setNum(newNum);
        }
        return true;
    }

    /**
     * 根据道具id获取道具信息
     *
     * @param playerId
     * @param itemId
     * @return
     */
    public Item getItem(Integer playerId, Integer itemId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        throw new ItemIdErrorException();
    }

    /**
     * 根据道具typeId获取道具信息
     *
     * @param playerId
     * @param itemTypeId
     * @return
     */
    public Item getItemByTypeId(Integer playerId, Integer itemTypeId) {
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        // 遍历道具
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getTypeId().equals(itemTypeId)) {
                return item;
            }
        }
        return null;
    }
}

