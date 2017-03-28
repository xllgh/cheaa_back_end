package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.resvo.DeviceBindResVO;
import com.gizwits.bsh.bean.resvo.DeviceUnbindResVO;

/**
 * Created by zhl on 2016/11/21.
 */
public interface UserOperatorService {
    public void loginThirdPartyCloud();

    @Deprecated
    public ResultMap loadUserAllDevices();

    public ResultMap loadUserDevices(String userId);

    public ResultMap bindUserDevice(DeviceBindResVO vo);

    public ResultMap unbindUserDevice(DeviceUnbindResVO vo);
}
