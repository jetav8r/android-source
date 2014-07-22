package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

class Chihuahua extends Dog {   
    @Override
    void feed () {
        float newWeight = this.getWeight() + (mMeals * WEIGHT_GAIN);
        int sizeIncrement = (int) Math.floor(mMeals/5);
        String[] sizes = new String[] {"tiny", "small", "average", "large"}; 
        List<String> sizeList = Arrays.asList(sizes);
        int index = sizeList.indexOf(mSize);
        int newSize = index + sizeIncrement;
        if (newSize > 3) {
            newSize = 3;
        }
        switch (newSize) {
            case 0:
                System.out.println("Your dog's current size is tiny");
                break;
            case 1:
                System.out.println("Your dog's current size is small");
                break;
            case 2:
                System.out.println("Your dog's current size is average");
                break;
            case 3:
                System.out.println("Your dog's current size is large");
                break;    
        }
    } 
}