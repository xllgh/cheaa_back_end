package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.reqvo.web.AdministratorListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.model.entity.SystemUser;

public interface AdministratorService {
    ReviewVO<SystemUser> getAdministratorList(AdministratorListReqVO reqVO);

    boolean checkUsername(String username);

    BaseRespVO saveNewAdministrator(SystemUser systemUser);

    BaseRespVO deleteAdministrator(String id);

    String getAdministratorRole();
}
