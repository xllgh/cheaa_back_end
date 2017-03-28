package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.reqvo.web.DeviceListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.model.entity.Device;

import java.util.Map;

/**
 * Created by zhl on 2016/12/6.
 */
public interface DeviceService extends BaseService<Device,Long> {
    ReviewVO<Map> getDeviceList(DeviceListReqVO deviceListReqVO);
    BaseRespVO deleteDevice(String id);
}
