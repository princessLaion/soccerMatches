package com.lrp.core.exception;

public class ErrorInfo {
	private String errCode;
	private String errMessage;
	
	public ErrorInfo(String errCode, String errMessage) {
		this.errCode = errCode;
		this.errMessage = errMessage;
	}
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
}
