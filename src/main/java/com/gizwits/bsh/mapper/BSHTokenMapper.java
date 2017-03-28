package com.gizwits.bsh.mapper;

import com.gizwits.bsh.model.entity.BSHToken;
import tk.mybatis.mapper.common.Mapper;

public interface BSHTokenMapper extends Mapper<BSHToken> {
    /**
     * 清空access_token
     * @param owner
     * @return
     */
    boolean clearAccessToken(String owner);
}
