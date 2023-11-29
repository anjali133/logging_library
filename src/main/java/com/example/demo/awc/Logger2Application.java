package com.example.demo.awc;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication

public class Logger2Application {

	public static void main(String[] args) {
		//Configurator.initialize(null, "log4j2.xml");
		ApplicationContext ctx = SpringApplication.run(Logger2Application.class, args);
		CustomLogger logger = ctx.getBean(CustomLogger.class);
		logger.CustomLogger("awc");
		logger.writeError("E001", new JSONObject(), "Error occurred", new RuntimeException("Example exception"));
        //logger.writeWarning("W001", new JSONObject(), "Warning message");
        logger.writeInfo("I001", "Information message");
        //logger.writeDebug("D001", new JSONObject(), "Debug message");
		
		
       ((ConfigurableApplicationContext)ctx).close();
	}

}
