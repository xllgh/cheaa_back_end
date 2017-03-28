package com.gizwits.bsh.mapper;

import com.gizwits.bsh.model.entity.DeviceAttributeAdapter;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zhl on 2016/12/5.
 */
public interface DeviceAttributeAdapterMapper extends Mapper<DeviceAttributeAdapter> {
    List<DeviceAttributeAdapter> selectByDeviceType(DeviceAttributeAdapter adapter);
    List<DeviceAttributeAdapter> selectGroupByPlatIdAndDeviceType();
}
