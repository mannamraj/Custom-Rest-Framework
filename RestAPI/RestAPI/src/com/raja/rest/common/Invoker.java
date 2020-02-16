package com.raja.rest.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Invoker<T,M> {

	void invoke(T config,HttpServletRequest request,HttpServletResponse response);
	
	Invoker<T, M> setMapper(M mapper);
	
}
