package com.gizwits.bsh.service.impl;

import com.gizwits.bsh.bean.reqvo.web.UserDeviceListReqVO;
import com.gizwits.bsh.bean.reqvo.web.UserListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.bean.resvo.web.UserDeviceVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.mapper.DeviceMapper;
import com.gizwits.bsh.mapper.PlatUserMapper;
import com.gizwits.bsh.model.entity.PlatUser;
import com.gizwits.bsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<PlatUser,Long> implements UserService {

    @Autowired
    PlatUserMapper platUserMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public ReviewVO<PlatUser> getUserList(UserListReqVO reqVO) {
        List users = platUserMapper.selectUserList(reqVO);
        ReviewVO result = new ReviewVO();
        result.setDraw(reqVO.getDraw());
        result.setData(users);
        result.setRecordsTotal(users.size());
        int total = platUserMapper.selectUserListCount(reqVO);
        result.setRecordsFiltered(total);
        return result;
    }

    @Override
    public PlatUser getUser(String userId) {
        return platUserMapper.selectByUserId(userId);
    }

    @Override
    public ReviewVO<UserDeviceVO> getUserDeviceList(UserDeviceListReqVO reqVO) {
        List deivces = deviceMapper.selectUserDeviceList(reqVO);
        ReviewVO result = new ReviewVO();
        result.setDraw(reqVO.getDraw());
        result.setData(deivces);
        result.setRecordsTotal(deivces.size());
        int total = deviceMapper.selectUserDeviceListCount(reqVO);
        result.setRecordsFiltered(total);
        return result;
    }

    @Override
    public BaseRespVO unbindDevice(String bindingId) {
        int rows = deviceMapper.unbindUserDeviceWithBindingId(bindingId);
        if (rows == 0) {
            return new BaseRespVO(ErrType.FAIL);
        }
        return new BaseRespVO(ErrType.SUCCESS);
    }

    public BaseRespVO changeUserPermission(PlatUser platUser) {
        if(platUserMapper.updateUserViewPermission(platUser)>0){
            return new BaseRespVO(ErrType.SUCCESS);
        }else{
            return new BaseRespVO(ErrType.FAIL);
        }
    }
}

