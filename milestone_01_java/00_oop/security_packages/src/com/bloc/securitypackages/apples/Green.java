package com.bloc.securitypackages.apples;

public class Green extends Apple {

	public Green() {
		super(Green.class.getSimpleName(), 230, new LimeGreen(), 0.21d);
	}

        @Override
	void bite() {
		setWeight(getWeight() - 0.02d);
	}

}