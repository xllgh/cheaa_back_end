package com.gizwits.bsh.service.impl;

import org.cheaa.interconnection.spi.IUDeviceIdConvert;
import org.cheaa.interconnection.type.BigCategoryType;
import org.cheaa.interconnection.type.MiddleCategoryType;
import org.cheaa.interconnection.client.sdk.ClientSDK;
import org.cheaa.interconnection.server.sdk.ServerSDK;
import com.gizwits.bsh.util.BaseKit;
import com.gizwits.bsh.bean.*;
import com.gizwits.bsh.model.entity.Device;
import com.gizwits.bsh.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UDeviceIdConvertImpl implements IUDeviceIdConvert {
	
	private static Logger logger = LoggerFactory.getLogger(UDeviceIdConvertImpl.class);

	@Autowired
	DeviceMapper deviceMapper;

	/*private void SDKinit(){
	try{
                        ServerSDK.instance().init("/home/ec2-user/CHEAA/CHEAA_latest/cheaa_back_end/src/main/resources/configure");
                        ClientSDK.instance().init("/home/ec2-user/CHEAA/CHEAA_latest/cheaa_back_end/src/main/resources/configure");
                        logger.info("SDK inited and platid is " + ClientSDK.instance().getPLAT_ID());
                        }catch (Exception e){logger.info(""+e);}

	}*/

	private BSHDeviceType getDeviceType(String deviceId){
		Device record = new Device();
		record.setPlatId(ClientSDK.instance().getPLAT_ID());
		record.setDeviceId(deviceId);
		try {
			Device device = deviceMapper.select(record).get(0);
			if (device.getDeviceType().equalsIgnoreCase(BSHDeviceType.Oven.toString())){
				return BSHDeviceType.Oven;				
			}else if(device.getDeviceType().equalsIgnoreCase(BSHDeviceType.CoffeeMaker.toString())){
				return BSHDeviceType.CoffeeMaker;}			
	}catch (Exception e){
			logger.info(""+e);
				}
		return null;
}
	@Override
	public String encode(String s)
	{
	//	SDKinit();
		logger.info("Do you know the type?");
		BSHDeviceType deviceType = getDeviceType(s);
		if (deviceType == null){
			return s;}
		switch(deviceType) {
			case Oven:{
				s = BaseKit.encodeBase(s);
				logger.info(s);
				return ClientSDK.instance().getPLAT_ID() + "01" + BigCategoryType._04.getType() + MiddleCategoryType._04_001.getType() + s;
			}
			case CoffeeMaker:
			{
				s = BaseKit.encodeBase(s);
				logger.info(s);
				return ClientSDK.instance().getPLAT_ID() + "01" + BigCategoryType._04.getType() + MiddleCategoryType._04_010.getType() + s;
			}
			default: {return s;}
	}}
	@Override
	public String decode(String s)
	{
	//	SDKinit();
		String platId = s.substring(0,6);
		logger.info(platId);
		if(!(platId.equals(ClientSDK.instance().getPLAT_ID())))
		{	
			logger.info("The platId" + platId + "is not equals to " + ClientSDK.instance().getPLAT_ID());
			return null;
		}
		s = s.substring(13);
		logger.info(s);
		s = BaseKit.decodeBase(s);
		logger.info(s);
		return s;
	}
}
