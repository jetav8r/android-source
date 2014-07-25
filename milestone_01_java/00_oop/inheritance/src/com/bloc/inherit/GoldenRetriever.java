package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

public class GoldenRetriever extends Dog {   
    @Override
    public void play () {
        setWeight(getWeight() - WEIGHT_LOST_FROM_FEEDING);
	if (getWeight() < MINIMUM_WEIGHT) {
            setWeight(MINIMUM_WEIGHT);
        }
	// Pre-increment play counter
	if (++mPlayCounter == 3) {
            changeSize(false);
            mPlayCounter = 0;
        }
    }
}