package com.raja.rest.enums;

public enum ErrorType {

	NULL_VALUE("Null value"),
	LENGTH_EXCEED("Length Exceed");
	
	private String message;
	
	ErrorType(){
	}
	
	
	ErrorType(String message){
		this.message = message;
	}
	
	public String get(){
		
		return message;
	}

}
