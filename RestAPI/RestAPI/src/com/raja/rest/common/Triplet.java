package com.raja.rest.common;

public class Triplet<T1,T2,T3> {
	
	private T1 one;

	private T2 two;

	private T3 three;

	public T1 getOne() {
		return one;
	}

	public void setOne(T1 one) {
		this.one = one;
	}

	public T2 getTwo() {
		return two;
	}

	public void setTwo(T2 two) {
		this.two = two;
	}

	public T3 getThree() {
		return three;
	}

	public void setThree(T3 three) {
		this.three = three;
	}
}
