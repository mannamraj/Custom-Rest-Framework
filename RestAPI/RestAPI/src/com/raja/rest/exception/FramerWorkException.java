package com.raja.rest.exception;

public class FramerWorkException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public FramerWorkException(Throwable throwable){
		super(throwable);
	}
	
	public FramerWorkException(Throwable throwable,String message){
		super(message,throwable);
	}
	
	public FramerWorkException(String message){
		super(message);
	}
}
