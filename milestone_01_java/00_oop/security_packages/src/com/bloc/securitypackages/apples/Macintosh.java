package com.bloc.securitypackages.apples;

import com.bloc.securitypackages.Fruit;
import com.bloc.securitypackages.colors.*;

public class Macintosh extends Fruit {

	public Macintosh() {
		super(Macintosh.class.getSimpleName(), 200, new Red(), 0.14d);
	}

	void bite() {
		setWeight(getWeight() - 0.01d);
	}

}