package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.domain.opBean.RsqBasicOP;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhl on 2016/11/21.
 */
public interface DeviceOperatorService {

    public ResultMap handleOperatorCommand(RsqBasicOP rsqBasicOP);

    public ResultMap handleOperatorRequest(RsqBasicOP rsqBasicOP);

    public ResultMap handleDeviceStatusRequest(String deviceId);

    public ResultMap loadAllDeviceType();

    public ResultMap getDeviceTypeAndPlatId(String typeCode,String platId);
    public ResultMap getDeviceType(String typeCode);
}
