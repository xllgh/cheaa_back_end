package com.gizwits.bsh.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gizwits.bsh.service.HomeConnectService;
import com.gizwits.bsh.bean.RetObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;

@Component
public class ScheduleTask{

	private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

	@Autowired
	HomeConnectService homeConnectService;

	@Scheduled(fixedRate = 1000 * 70 )
	public void taskCycle(){
		RetObject result = RetObject.init();
		try{
			//result = homeConnectService.dataGateway("cheaashowcase2018@mailinator.com","0000030104010U0lFTUVOUy1DVDYzNkxFUzZXLTY4QTQwRTAxNDZBRA","000011");
			//logger.info("Gateway result to Konka: " + result.getRetCode() + result.getRetInfo());
			result = homeConnectService.dataGateway("cheaahaiergateway@sharklasers.com","0000030104001Qk9TQ0gtSFNHNjU2WFM2Vy02OEE0MEUxMDBFMzk","000006");
			//result = homeConnectService.dataGateway("cheaa")
			logger.info("Gateway result to Haier: " + result.getRetCode() + result.getRetInfo());
			}catch (Exception exception){
				exception.printStackTrace();
		}
	}
	
	@Scheduled(fixedRate = 1000 * 300 )
	public void taskHAlist(){
		DeviceListResVO result = new DeviceListResVO(RetObject.fail());
		try{
			result = homeConnectService.getDevices("cheaahaiergateway@sharklasers.com","");
			logger.info("Gateway of HA List result is ok");
		}catch (Exception exception){
			exception.printStackTrace();
			}
}
} 
