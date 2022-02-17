package com.fattyCorps.obj.msg.server.item;

import com.fattyCorps.obj.db.Hero;
import com.fattyCorps.obj.db.Item;
import lombok.Data;

import java.util.List;

@Data
public class SGetItemListMsg {
    private List<Item> itemList;
}
