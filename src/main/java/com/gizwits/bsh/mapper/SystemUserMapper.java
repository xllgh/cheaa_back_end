package com.gizwits.bsh.mapper;

import com.gizwits.bsh.bean.reqvo.web.AdministratorListReqVO;
import com.gizwits.bsh.model.entity.SystemUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SystemUserMapper extends Mapper<SystemUser> {
    SystemUser selectUserByUsername(String username);

    List<SystemUser> selectSystemUserList(AdministratorListReqVO reqVO);
    int selectSystemUserListCount(AdministratorListReqVO reqVO);

    List<SystemUser> selectSysetemUserByUsername(String username);

    int deleteSystemUser(String id);
}