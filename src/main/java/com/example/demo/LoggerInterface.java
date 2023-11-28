package com.example.demo;

import org.json.JSONObject;

public interface LoggerInterface {
	void writeError(String messageCode, JSONObject messageDetail, String message, Exception stackTrace);
    void writeWarning(String messageCode, JSONObject messageDetail, String message);
    void writeInfo(String messageCode, String message);
    void writeDebug(String messageCode, JSONObject messageDetail, String message);
    boolean isDebugEnabled();
    boolean isInfoEnabled();


}
