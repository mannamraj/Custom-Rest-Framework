package com.raja.rest.common;

import java.util.List;

public interface Loader<T,T1> {

	public List<T> load(T1 source);
}
