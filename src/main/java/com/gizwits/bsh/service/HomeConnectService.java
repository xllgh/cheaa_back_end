package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.BSHDevice;
import com.gizwits.bsh.bean.HomeAppliance;
import com.gizwits.bsh.bean.HomeApplianceStatus;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.bean.resvo.DeviceResVO;
import com.gizwits.bsh.enums.DeviceStatus;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * 博西 Home Connect API
 */
public interface HomeConnectService {

    /**
     * 获取设备列表
     * @return
     */
    DeviceListResVO getDevices(String user, String deviceType);

    DeviceResVO getDeviceInfo(String user, String deviceID);

    /**
     * 获取设备状态
     * @param deviceId
     * @return
     */
    DeviceStatusResVO getDeviceStatus(String user, String deviceId);

    /**
     * 操作设备
     */
    RetObject operateDevice(String user, String deviceId, ModelMap attr);

    RetObject dataGateway(String user, String deviceId,String platID);
   
    RetObject dataGatewayCoffee(String user, String deviceID, String platID);
 
    Boolean isPlatIdSupport(String platId);
}
