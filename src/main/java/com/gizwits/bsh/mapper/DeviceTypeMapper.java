package com.gizwits.bsh.mapper;

import com.gizwits.bsh.model.entity.DeviceType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;


/**
 * Created by zhl on 2016/11/30.
 */
public interface DeviceTypeMapper extends Mapper<DeviceType> {
    List<DeviceType> selectAllDeviceType();

    Map<String,String> selectByDeviceCodeAndPlatId(DeviceType deviceType);
    Map<String,String> selectByDeviceCode(String typeCode);
}
