package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.HttpRspObject;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.resvo.DeviceBindResVO;
import com.gizwits.bsh.bean.resvo.DeviceUnbindResVO;
import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.common.config.SysConsts;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.enums.MultiCloudHost;
import com.gizwits.bsh.mapper.DeviceMapper;
import com.gizwits.bsh.mapper.DeviceTypeMapper;
import com.gizwits.bsh.mapper.PlatMapper;
import com.gizwits.bsh.mapper.PlatUserMapper;
import com.gizwits.bsh.model.entity.Device;
import com.gizwits.bsh.model.entity.DeviceType;

import com.gizwits.bsh.model.entity.Plat;
import com.gizwits.bsh.model.entity.PlatUser;
import com.gizwits.bsh.service.PlatService;
import com.gizwits.bsh.service.UserOperatorService;
import com.gizwits.bsh.util.GeneratorKit;
import com.gizwits.bsh.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhl on 2016/11/21.
 */
@Service
public class UserOperatorServiceImpl extends BaseServiceImpl<PlatUser,Long> implements UserOperatorService {
    private static final Logger logger = LoggerFactory.getLogger(UserOperatorServiceImpl.class);

    @Autowired
    private PlatService platService;

    @Autowired
    private PlatMapper platMapper;

    @Autowired
    private PlatUserMapper platUserMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    /**
     * 加载用户所有设备
     * @param userId
     * @return
     */
    @Override
    public ResultMap loadUserDevices(String userId) {
        if(platUserMapper.selectByUserId(userId).getIsAll()>0){//全列表
            return new ResultMap(ErrType.SUCCESS,deviceMapper.selectAllValidDevice());
        }
        return new ResultMap(ErrType.SUCCESS,deviceMapper.selectByUserId(userId));
    }

    /**
     * 用户绑定设备
     * @param vo
     * @return
     */
    @Override
    public ResultMap bindUserDevice(DeviceBindResVO vo) {
        if ( deviceMapper.selectByUserIdAndDeviceId(vo)!=null) {
            return new ResultMap(ErrType.DEVICE_EXISTED);
        }
        if ( platMapper.selectByPlatId(vo.getPlatID()) == null) {
            return new ResultMap(ErrType.PLAT_NOTEXISTED);
        }
        if ( deviceTypeMapper.selectByDeviceCodeAndPlatId(new DeviceType(vo.getPlatID(),vo.getDeviceType()))==null){
            return new ResultMap(ErrType.DEVICE_TYPE_NOTEXISTED);
        }
        saveUserIfNotExisted(platUserMapper.selectByUserId(vo.getUserID()),vo);
        return saveBindDevice(saveDeviceIfNotExisted(deviceMapper.selectByDeviceId(vo.getDeviceID()),vo),vo);
    }

    private Device saveDeviceIfNotExisted(Device device,DeviceBindResVO vo){
        if(device==null){
            device = new Device();
            device.setId(GeneratorKit.getID());
            device.setDeviceId(vo.getDeviceID());

            device.setPlatId(vo.getPlatID());
            device.setCreator(vo.getUserID());
            device.setCreateTime(new Date());
            device.setDeviceType(vo.getDeviceType());
            Plat plat = platMapper.selectByPlatId(vo.getPlatID());
            Map<String,String> deviceType = deviceTypeMapper.selectByDeviceCodeAndPlatId(new DeviceType(vo.getPlatID(),vo.getDeviceType()));
            device.setName(plat.getDescription()+deviceType.get("description").toString());

            //恶心的美的,请求数据的时候用户ID必须是1000002,,,,~_~||
            if(device.getName().indexOf("美的")>=0){
                device.setOwnerId("1000002");
            }else{
                device.setOwnerId(vo.getUserID());
            }
            deviceMapper.insertSelective(device);
        }
        return device;
    }

    private void saveUserIfNotExisted(PlatUser user,DeviceBindResVO vo){
        if(user==null){
            user = new PlatUser();
            user.setId(GeneratorKit.getID());
            user.setUserId(vo.getUserID());
            user.setPlatId(vo.getPlatID());
            user.setFirstLoginTime(new Date());
            user.setLastLoginTime(new Date());
            user.setUserName(vo.getUserName());
            platUserMapper.insert(user);
        }
    }

    private ResultMap saveBindDevice(Device device,DeviceBindResVO vo){
        device.setId(GeneratorKit.getID());//绑定表生成一个新的
        device.setOwnerId(vo.getUserID());
        if (deviceMapper.selectByUserIdAndDeviceId(vo)!=null) {
            return new ResultMap(ErrType.DEVICE_EXISTED);
        }
        Plat plat = platMapper.selectByPlatId(vo.getPlatID());
        if (plat == null) {
            return new ResultMap(ErrType.PLAT_NOTEXISTED);
        }
        PlatUser user = platUserMapper.selectByUserId(vo.getUserID());
        if (device == null) {
            device = saveDevice(vo);
        }
        if (user == null) {
            saveUser(vo);
        }
        return saveBindDevice(device);
    }

    public Device saveDevice(DeviceBindResVO vo){
        Device device = new Device();
        device.setId(GeneratorKit.getID());
        device.setDeviceId(vo.getDeviceID());
        device.setOwnerId(vo.getUserID());
        device.setPlatId(vo.getPlatID());
        device.setCreator(vo.getUserID());
        device.setCreateTime(new Date());
        device.setDeviceType(vo.getDeviceType());
        deviceMapper.insertSelective(device);
        return device;
    }

    public void saveUser(DeviceBindResVO vo){
        PlatUser user = new PlatUser();
        user.setId(GeneratorKit.getID());
        user.setUserId(vo.getUserID());
        user.setPlatId(vo.getPlatID());
        user.setFirstLoginTime(new Date());
        user.setLastLoginTime(new Date());
        user.setUserName(vo.getUserName());
        platUserMapper.insert(user);
    }

    private ResultMap saveBindDevice(Device device){
        device.setId(GeneratorKit.getID());//绑定表生成一个新的
        if (deviceMapper.bindUserDevice(device) > 0){
            return new ResultMap(ErrType.SUCCESS);
        }else {
            return new ResultMap(ErrType.FAIL);
        }
    }

    /**
     * 用户解除设备绑定
     * @param vo
     * @return
     */
    @Override
    public ResultMap unbindUserDevice(DeviceUnbindResVO vo) {
        if (deviceMapper.selectByUserIdAndDeviceId(new DeviceBindResVO(vo.getUserID(),vo.getDeviceID()))==null) {
            return new ResultMap(ErrType.DEVICE_BIND_NOTEXISTED);
        }
        if(deviceMapper.unbindUserDevice(vo)>0){
            return new ResultMap(ErrType.SUCCESS);
        }else{
            return new ResultMap(ErrType.FAIL);
        }
    }


    @Override
    public ResultMap loadUserAllDevices() {
        Plat tmp = new Plat();
        tmp.setAppId(EnvSetting.AppID);
        tmp.setPlatId(EnvSetting.PlatID);

        //// TODO: 2016/11/24 登录刷新token
        loginThirdPartyCloud();

        Plat plat = platMapper.selectOne(tmp);
        PlatUser user = platUserMapper.selectByPlatId(EnvSetting.PlatID);

        Map<String, String> headers = new HashMap<>();
        headers.put("PlatID", EnvSetting.PlatID);
        headers.put("AppID", EnvSetting.AppID);
        headers.put("AccessToken", plat.getAccessToken());
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String param = "UserID=" + user.getUserId();
        //getHaixerDevices(headers,param);
        //JSONArray jsonArray = getHaixinDevices(headers,param);


        return getHaixinDevices(headers,param);
        //return null;
    }

    @Override
    public void loginThirdPartyCloud() {

        String host = MultiCloudHost.HAIXIN.getHost();

        Map<String, String> headers = new HashMap<>();
        headers.put("PlatID", EnvSetting.PlatID);
        headers.put("AppID", EnvSetting.AppID);
        headers.put("AuthToken", EnvSetting.AuthToken);
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String param = "AccType=" + EnvSetting.AccType + "&UUserID=" + EnvSetting.UUserID;
        try {
            loginHaxin(headers,param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("第三方登录验证出错host:{}", host);
        }
    }

    /**
     * 海信登录获取token
     * @param headers
     * @param param
     */
    public String loginHaxin(Map<String,String> headers,String param){
        logger.info("===start login haxin====");
        logger.info("===login headers    :{}====",headers.toString());
        logger.info("===login param      :{}====",param);
        HttpRspObject httpRspObject = HttpUtil.post(SysConsts.HOST_HAIXIN+"1.0/user/login", headers, param, "application/x-www-form-urlencoded");
        logger.info("===get login response:{}====",httpRspObject.getBody());
        JSONObject jsonObject = JSON.parseObject(httpRspObject.getBody());
        String accessToken = "";
        if(jsonObject.getInteger("RetCode")==200){
            String userId = jsonObject.getString("UserID");
            accessToken = httpRspObject.getHeadersMap().get("AccessToken");
            platService.refreshToken(headers.get("PlatID"),headers.get("AppID"),accessToken,userId);
            logger.info("===get login accessToken:{}====",accessToken);
        }else{
            logger.info("===login fail!!! RetCode:{} RetInfo:{}====",jsonObject.getString("RetCode"),jsonObject.getString("RetInfo"));
        }
        return accessToken;
    }

    public JSONArray getDevices(){

        return null;
    }

    public ResultMap getHaixinDevices(Map<String,String> headers,String param){
        ResultMap resultMap = new ResultMap();
        JSONArray returnArr = new JSONArray();
        logger.info("===start login haxin====");
        logger.info("===login headers    :{}====",headers.toString());
        logger.info("===login param      :{}====",param);
        HttpRspObject httpRspObject = HttpUtil.post(SysConsts.HOST_HAIXIN+"1.0/user/devices", headers, param, "application/x-www-form-urlencoded");
        logger.info("===get login response:{}====",httpRspObject.getBody());
        JSONObject jsonObject = JSON.parseObject(httpRspObject.getBody());
        if(jsonObject.getInteger("RetCode")==200){
            JSONObject returnJSON = new JSONObject();

            final JSONArray devices = JSON.parseObject(httpRspObject.getBody()).getJSONArray("Devices");

            if (devices != null && devices.size() > 0) {
                for (int i = 0; i < devices.size(); i++) {
                    JSONObject jsonObject1 = devices.getJSONObject(i);
                        //returnArr.add(jsonObject1);
                    }
            }

//            //// TODO: 2016/11/24
            JSONObject tmpObj = new JSONObject();

            tmpObj.put("DeviceID","86100c009002005000000101959525e21ur02000nljk00h4jj10811");
            tmpObj.put("DeviceAttrs","{}");
            tmpObj.put("name","油烟机");
            tmpObj.put("mac","01959525e1");
            tmpObj.put("DeviceType","010404");
            returnArr.add(tmpObj);

            tmpObj = new JSONObject();
            tmpObj.put("DeviceID","86100c009002002000000101959525a31b026000601j00h4gp10813");
            tmpObj.put("DeviceAttrs","{}");
            tmpObj.put("name","冰箱");
            tmpObj.put("mac","01959525a0");
            tmpObj.put("DeviceType","010103");
            returnArr.add(tmpObj);


            tmpObj = new JSONObject();
            tmpObj.put("DeviceID","0007A8B4F71B");
            tmpObj.put("DeviceAttrs","{}");
            tmpObj.put("name","测试空调");
            tmpObj.put("mac","***");
            tmpObj.put("DeviceType","03001012");
            returnArr.add(tmpObj);

            returnJSON.put("Devices", returnArr);
            resultMap.setFlag(true);
            resultMap.setMsg("请求成功");
            resultMap.setData(returnJSON);

        }else{
            resultMap.setFlag(false);
            resultMap.setMsg("请求失败");
            resultMap.setData(null);
        }
        logger.info("====get resultMap :{}",resultMap.toString());
        return resultMap;
    }

    public JSONArray getHaixerDevices(Map<String,String> headers,String param){
        ResultMap resultMap = new ResultMap();
        JSONArray returnArr = new JSONArray();

        HttpRspObject httpRspObject = HttpUtil.post(SysConsts.HOST_HAIER+"user/devices", headers, param, "application/json");
        logger.info("===get login response:{}====",httpRspObject.getBody());
        JSONObject jsonObject = JSON.parseObject(httpRspObject.getBody());
        if(jsonObject.getInteger("RetCode")==200){
            JSONObject returnJSON = new JSONObject();

            final JSONArray devices = JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device");

            if (devices != null && devices.size() > 0) {
                for (int i = 0; i < devices.size(); i++) {
                    JSONObject jsonObject1 = devices.getJSONObject(i);
                    returnArr.add(jsonObject1);
                }
            }
            returnJSON.put("Devices", returnArr);
//            resultMap.setFlag(true);
//            resultMap.setMsg("请求成功");
//            resultMap.setData(returnJSON);

        }else{
//            resultMap.setFlag(false);
//            resultMap.setMsg("请求失败");
//            resultMap.setData(null);
            returnArr = null;
        }
        logger.info("====get resultMap :{}",returnArr);
        return returnArr;
    }
}
