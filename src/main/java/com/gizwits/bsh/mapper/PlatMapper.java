package com.gizwits.bsh.mapper;

import com.gizwits.bsh.model.entity.Plat;
import tk.mybatis.mapper.common.Mapper;

public interface PlatMapper extends Mapper<Plat> {

    /**
     * 清空access_token
     * @param platID
     * @return
     */
    boolean clearAccessToken(String platID);

    Plat selectByPlatId(String platId);
}