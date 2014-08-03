package com.bloc.securitypackages.apples;

public class Macintosh extends Apple {

	public Macintosh() {
		super(Macintosh.class.getSimpleName(), 200, new Red(), 0.14d);
	}

        @Override
	void bite() {
		setWeight(getWeight() - 0.01d);
	}

}