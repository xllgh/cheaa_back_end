package com.gizwits.bsh.mapper;

import com.gizwits.bsh.bean.reqvo.web.DeviceListReqVO;
import com.gizwits.bsh.bean.reqvo.web.UserDeviceListReqVO;
import com.gizwits.bsh.bean.resvo.DeviceBindResVO;
import com.gizwits.bsh.bean.resvo.DeviceUnbindResVO;
import com.gizwits.bsh.bean.resvo.web.UserDeviceVO;
import com.gizwits.bsh.model.entity.Device;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface DeviceMapper extends Mapper<Device> {
    Device selectByDeviceId(String deviceId);
    List<Device> selectDevices();

    List<Device> selectByUserId(String userId);

    List<Device> selectAllValidDevice();

    Device selectByUserIdAndDeviceId(DeviceBindResVO vo);

    int bindUserDevice(Device device);

    int unbindUserDevice(DeviceUnbindResVO vo);

    List<Map> selectDeviceList(DeviceListReqVO vo);
    int selectDeviceListCount(DeviceListReqVO vo);

    int deleteDevice(String deviceId);
    int unbindDeletedDevice(String deviceId);

    List<UserDeviceVO> selectUserDeviceList(UserDeviceListReqVO reqVO);
    int selectUserDeviceListCount(UserDeviceListReqVO reqVO);

    int unbindUserDeviceWithBindingId(String bindingId);
}