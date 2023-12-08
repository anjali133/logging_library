package sentioza.awc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

@Component("awc")
public class CustomLogger implements LoggerInterface{

    private Logger logger;

    public void CustomLogger(String loggerName) {
    	this.logger = LogManager.getLogger(loggerName);
    }
    @Override
    public void writeError(String messageCode, JSONObject messageDetail, String message, Exception stackTrace) {
        if (logger.isErrorEnabled()) {
            JSONObject logObject = buildLogObject(messageCode, LogLevel.ERROR, message);
            logger.error(logObject.toString(), stackTrace);
        } 							
    }
    @Override
    public void writeWarning(String messageCode, JSONObject messageDetail, String message) {
        if (logger.isWarnEnabled()) {
            JSONObject logObject = buildLogObject(messageCode, LogLevel.WARN, message);
            logger.warn(logObject.toString());
        }
    }
    @Override
    public void writeInfo(String messageCode, String message) {
        if (logger.isInfoEnabled()) {
            JSONObject logObject = buildLogObject(messageCode, LogLevel.INFO, message);
            logger.info(logObject.toString());
        }
    }
    @Override
    public void writeDebug(String messageCode, JSONObject messageDetail, String message) {
        if (logger.isDebugEnabled()) {
            JSONObject logObject = buildLogObject(messageCode, LogLevel.DEBUG, message);
            logger.debug(logObject.toString());
        }
    }
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }
    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }
    private JSONObject buildDeviceInfo() {
    	JSONObject deviceInfo = new JSONObject();
        try {
			InetAddress localhost = InetAddress.getLocalHost();
			 deviceInfo.put("name", localhost.getHostName());
	            deviceInfo.put("id", localhost.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			deviceInfo.put("name", "unknown");
            deviceInfo.put("id", "unknown");
		}
        deviceInfo.put("type", System.getProperty("os.arch"));
        deviceInfo.put("operatingSystem", System.getProperty("os.name"));
        deviceInfo.put("osVersion", System.getProperty("os.version"));
        return deviceInfo;
    }
    private JSONObject buildUserInfo() {
    	JSONObject userInfo = new JSONObject();
        userInfo.put("userId", "");
        userInfo.put("tenentId", "");
        userInfo.put("clientId", "");
        return userInfo;
    }   
    private JSONObject buildInfoMessageDetail() {
   	 JSONObject messageDetail = new JSONObject();
    	messageDetail.put("correlationId", "e5b3d8a2-e523-4e3f-ac7a-b028a2ff3014");
    	messageDetail.put("path", "/aware-care");
    	messageDetail.put("method", "POST");
    	messageDetail.put("requestHeaders", "{}");
    	messageDetail.put("requestBody", "{}");
        return messageDetail;
    }
    private JSONObject buildRequestingDeviceInfo() {
    	 JSONObject requestingDeviceInfo = new JSONObject();
    	requestingDeviceInfo.put("name", "UI device name");
    	requestingDeviceInfo.put("operatingSystem", "xyz");
        return requestingDeviceInfo;
    }
    private JSONObject buildErrorMessageDetail() {
    	 JSONObject messageDetail = new JSONObject();
    	  try {
  			InetAddress localhost = InetAddress.getLocalHost();
  		 	messageDetail.put("code", "ECONNREFUSED");
  		 	messageDetail.put("address", localhost.getHostAddress());
  			messageDetail.put("port", 6379);
  		} catch (UnknownHostException e) {
  			e.printStackTrace();
  			messageDetail.put("code", "unknown");
  			messageDetail.put("address", "unknown");
  		}
        return messageDetail;
    }
    private JSONObject buildLogObject(String messageCode, LogLevel severity, String message) {
        JSONObject logObject = new JSONObject();
        logObject.put("componentName", "awarecare");
        logObject.put("severity", severity.name());
        logObject.put("componentVersion", "1.0.0");
        logObject.put("hostName", "awarecare-1234");
        logObject.put("messageCode", messageCode);
        logObject.put("message", message);
        logObject.put("conversationId", "f5b3d8a2-e523-4e3f-ac7a-b028a2ff3014");
        logObject.put("correlationId", "e5b3d8a2-e523-4e3f-ac7a-b028a2ff3014");
        logObject.put("deviceInfo", buildDeviceInfo());
        logObject.put("userInfo", buildUserInfo());
        logObject.put("timestamp", Instant.now().toString());
        
        if(severity == LogLevel.INFO || severity == LogLevel.WARN || severity == LogLevel.DEBUG) {
        	logObject.put("messageDetail", buildInfoMessageDetail());
        }     
        if(severity == LogLevel.ERROR) {
        	logObject.put("messageDetail", buildErrorMessageDetail());
        	logObject.put("requestingDeviceInfo", buildRequestingDeviceInfo());
        	logObject.put("category", "CONNECTIVITY_ISSUE");
        	logObject.put("subCategory", "DB_CONNECTION_ERROR");
        	logObject.put("stackTrace", "Error: connect ECONNREFUSED 127.0.0.1:5342\n");
        }
       return logObject;
    }
}