package com.bangduoduo.upload;

public class UploadMessage {
	private int error;
	
	private String message;
	
	private String url;
	
	public UploadMessage(){}
	
	public UploadMessage(int error,String message,String url){
		this.error = error;
		this.message = message;
		this.url = url;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
