package com.gizwits.bsh.mapper;

import com.gizwits.bsh.bean.reqvo.web.UserListReqVO;
import com.gizwits.bsh.model.entity.PlatUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PlatUserMapper extends Mapper<PlatUser> {
    public PlatUser selectByPlatId(String platId);
    public PlatUser selectByUserId(String userId);

    List<PlatUser> selectUserList(UserListReqVO reqVO);
    int selectUserListCount(UserListReqVO reqVO);

    int updateUserViewPermission(PlatUser platUser);
}