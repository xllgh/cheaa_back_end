package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.BSHDevice;
import com.gizwits.bsh.bean.HomeAppliance;
import com.gizwits.bsh.bean.HomeApplianceStatus;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.enums.DeviceStatus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.gizwits.bsh.bean.*;
import com.gizwits.bsh.bean.reqvo.*;
import com.gizwits.domain.opBean.RsqBasicOP;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public interface MykieService{
	

     /**
     * 获取设备列表
     * @return
     */
    DeviceListResVO getDevices(String user,String deviceType);

    /**
     * 获取设备状态
     * @param deviceId
     * @return
     */
    JSONObject getDeviceStatus(String user, String deviceId, String targetPlat);

    /**
     * 操作设备
     */
    JSONObject operateDevice(RsqBasicOP rspBasicOP, String targetPlat);


}
