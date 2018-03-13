package com.gizwits;
import org.cheaa.interconnection.server.sdk.ServerSDK;
import org.cheaa.interconnection.client.sdk.ClientSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ImportResource;

//@SpringBootApplication
public class BshApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(BshApplication.class);

	public static void main(String[] args) {
	//	SpringApplication.run(BshApplication.class, args);
		try {
			ServerSDK.instance().init("/home/ec2-user/CHEAA/CHEAA_latest/cheaa_back_end/src/main/resources/configure");
			ClientSDK.instance().init("/home/ec2-user/CHEAA/CHEAA_latest/cheaa_back_end/src/maib/resources/configure");
			logger.info("SDK init success");
			System.out.println("SDK Init success");
		} catch (Exception e){
			System.out.println("SDK init failed" + e);
			logger.error("SDK initialize failed.");
			System.exit(-1);
	}
}
}
