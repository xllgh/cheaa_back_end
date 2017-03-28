package com.gizwits.bsh.service.impl;

import com.gizwits.bsh.bean.reqvo.web.DeviceListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.mapper.DeviceMapper;
import com.gizwits.bsh.model.entity.Device;
import com.gizwits.bsh.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/12/6.
 */
@Service
public class DeviceServiceImpl extends BaseServiceImpl<Device,Long> implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public ReviewVO<Map> getDeviceList(DeviceListReqVO reqVO) {
        List devices = deviceMapper.selectDeviceList(reqVO);
        ReviewVO result = new ReviewVO();
        result.setDraw(reqVO.getDraw());
        result.setData(devices);
        result.setRecordsTotal(devices.size());
        int total = deviceMapper.selectDeviceListCount(reqVO);
        result.setRecordsFiltered(total);
        return result;
    }

    @Override
    public BaseRespVO deleteDevice(String deviceId) {
        if (deviceId == null) {
            return new BaseRespVO(ErrType.DEVICE_NOTEXISTED);
        }
        Device record = new Device();
        record.setDeviceId(deviceId);
        record.setIsAvail("1");
        List<Device> devices = deviceMapper.select(record);
        if (devices.size() == 0) {
            return new BaseRespVO(ErrType.DEVICE_NOTEXISTED);
        }


        int rows = deviceMapper.deleteDevice(deviceId);
        if (rows == 0) {
            return new BaseRespVO(ErrType.FAIL);
        }
        deviceMapper.unbindDeletedDevice(deviceId);

        return new BaseRespVO(ErrType.SUCCESS);
    }
}
