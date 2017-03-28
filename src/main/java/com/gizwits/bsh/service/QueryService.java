package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.ResultMap;

/**
 * Created by neil on 2016/11/5.
 */
public interface QueryService {

    ResultMap queryDevices(String username, String type);

    ResultMap queryDeviceStatus(String DeviceID);
}
