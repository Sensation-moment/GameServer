package com.fattyCorps.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fattyCorps.obj.db.Player;
import org.springframework.stereotype.Repository;

/**
 * 玩家映射接口
 */
@Repository
public interface PlayerMapper extends BaseMapper<Player> {
}
