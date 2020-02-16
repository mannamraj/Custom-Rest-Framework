package com.raja.rest.common;

public interface Handler<M,L> {

	M getMapper();	
	
	void load(M mapper,L loader);
}
