package com.fattyCorps.obj.msg.server.hero;

import com.fattyCorps.obj.db.Hero;
import lombok.Data;

import java.util.List;

@Data
public class SGetHeroListMsg {
    private List<Hero> heroList;
}
