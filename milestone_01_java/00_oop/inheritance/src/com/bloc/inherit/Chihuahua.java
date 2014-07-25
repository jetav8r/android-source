package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

public class Chihuahua extends Dog {   
    @Override
    public void feed () {
        mWeight += WEIGHT_GAINED_FROM_FEEDING;
	// Pre-increment feed counter
	if (++mFeedCounter == 5) {
		changeSize(true);
		mFeedCounter = 0;
        }
    } 
}