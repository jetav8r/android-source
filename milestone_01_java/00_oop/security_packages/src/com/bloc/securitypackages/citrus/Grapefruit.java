package com.bloc.securitypackages.citrus;

import com.bloc.securitypackages.Fruit;
import com.bloc.securitypackages.colors.*;

public class Grapefruit extends Fruit {
	public Grapefruit() {
		super(Grapefruit.class.getSimpleName(), 210, new OrangeRed(), .48d);
	}
}