package com.gizwits.bsh.common.config;

import com.gizwits.bsh.mapper.DeviceAttributeAdapterMapper;
import com.gizwits.bsh.model.entity.DeviceAttributeAdapter;
import com.gizwits.bsh.util.MapKit;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/11/21.
 */
public class SysConsts implements InitializingBean{

    private final static Logger logger = LoggerFactory.getLogger(SysConsts.class);

    public static final String SALT = "bsh2016";

    public static final String BSH_PLATID = "000003";

    public static final String PlatID = "MB-JDAPP-0000";
    public static final String AppID_HAIXIN = "17592186044420";
    public static final String AccType_HAIXIN = "9";
    public static final String UUserID = "test_00001";
    public static final String AuthToken = "tokennotusednow";

    /**************************************/
    /*********海信、海尔、美的接口地址*********/
    /**************************************/
    public static final String HOST_HAIXIN = "http://121.40.132.152";
    public static final String HOST_HAIER = "http://123.103.113.62:80/AWEConnectionDemo";
    public static final String HOST_MEIDI = "http://openapi.midea.com:9003/v1";
    /**海信**/
    public static final String HOST_HAIXIN_DEVICE_OPERATOR = HOST_HAIXIN+"/1.1/devices/op";
    public static final String HOST_HAIXIN_DEVICE_STATUS = HOST_HAIXIN+"/1.1/devices/deviceStatus";
    /**海尔**/
    public static final String HOST_HAIER_DEVICE_OPERATOR = HOST_HAIER+"/devices/op";
    public static final String HOST_HAIER_DEVICE_STATUS = HOST_HAIER+"/devices/deviceStatus";
    /**美的**/
    public static final String HOST_MEIDI_DEVICE_OPERATOR = HOST_MEIDI+"/devices/op";
    public static final String HOST_MEIDI_DEVICE_STATUS = HOST_MEIDI+"/devices/deviceStatus";


    public static final Map<String,Map> DeviceAttrAdapterMap = new HashedMap();
    public static final Map<String,Map> DeviceAttrAdapterReverseMap = new HashedMap();

    @Autowired
    private DeviceAttributeAdapterMapper deviceAttributeAdapterMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("====start loading system config=====>>>");
        init();
        logger.info("====finish loading system config====>>>");
    }


    private void init(){
        /**初始化t_device_attribute_adapter分组到内存**/
        List<DeviceAttributeAdapter> groupList = deviceAttributeAdapterMapper.selectGroupByPlatIdAndDeviceType();
        if(groupList!=null&& groupList.size()>0){
            for(DeviceAttributeAdapter adapter:groupList){
                DeviceAttrAdapterMap.put(adapter.getPlatId()+adapter.getDeviceType(),
                        MapKit.covertListToMapWithKeyAttr(deviceAttributeAdapterMapper.selectByDeviceType(adapter),"thirdAttr"));
                DeviceAttrAdapterReverseMap.put(adapter.getPlatId()+adapter.getDeviceType(),
                        MapKit.covertListToMapWithKeyAttr(deviceAttributeAdapterMapper.selectByDeviceType(adapter),"standardAttr"));
            }
        }
    }
}
