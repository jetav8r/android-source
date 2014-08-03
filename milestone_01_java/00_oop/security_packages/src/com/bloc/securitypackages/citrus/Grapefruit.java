package com.bloc.securitypackages.citrus;

import com.bloc.securitypackages.Fruit;

public class Grapefruit extends Fruit {
	public Grapefruit() {
		super(Grapefruit.class.getSimpleName(), 210, new com.bloc.securitypackages.colors.OrangeRed(), .48d);
	}
}