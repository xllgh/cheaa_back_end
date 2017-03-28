package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.reqvo.web.UserDeviceListReqVO;
import com.gizwits.bsh.bean.reqvo.web.UserListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.bean.resvo.web.UserDeviceVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.model.entity.PlatUser;

public interface UserService {

    ReviewVO<PlatUser> getUserList(UserListReqVO reqVO);

    PlatUser getUser(String userId);

    ReviewVO<UserDeviceVO> getUserDeviceList(UserDeviceListReqVO reqVO);

    BaseRespVO unbindDevice(String bindingId);

    BaseRespVO changeUserPermission(PlatUser platUser);
}
