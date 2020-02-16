package com.raja.rest.common;

import java.util.List;

public class MethodArguments<T> {

	private List<String> pathParams;
	
	private List<String> requstParams;
	
	private List<Class<T>> classAsParams;

	public List<String> getPathParams() {
		return pathParams;
	}

	public void setPathParams(List<String> pathParams) {
		this.pathParams = pathParams;
	}

	public List<String> getRequstParams() {
		return requstParams;
	}

	public void setRequstParams(List<String> requstParams) {
		this.requstParams = requstParams;
	}

	public List<Class<T>> getClassAsParams() {
		return classAsParams;
	}

	public void setClassAsParams(List<Class<T>> classAsParams) {
		this.classAsParams = classAsParams;
	}

}
