package com.bloc.interfaces.people;

import com.bloc.interfaces.people.hobbies.*;

public class Mary extends Person implements Skydiver {
	public Mary() {
		super("Mary", "Whiters", "Female", 1.65d, 62d, "Blue");
	}

        @Override
	public void getInPlane() {

	}

        @Override
	public void jumpFromPlane() {

	}

        @Override
	public void releaseParachute() {

	}
}