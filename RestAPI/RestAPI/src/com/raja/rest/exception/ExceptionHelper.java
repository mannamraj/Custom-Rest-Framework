package com.raja.rest.exception;

import com.raja.rest.enums.ErrorType;

public class ExceptionHelper {
	
	private ErrorType errorType;
	
	public ExceptionHelper(Builder builder){
		
		errorType = builder.errorType;
	}

	public Builder builder() {

		return new Builder();
		
	}
	
	class Builder{
		
		private ErrorType errorType;

		
		public Builder setErrorType(ErrorType errorType) {
			this.errorType = errorType;
			return this;
		}
		
		public ExceptionHelper build(){
		
			return new ExceptionHelper(this);
		}

	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}


}
