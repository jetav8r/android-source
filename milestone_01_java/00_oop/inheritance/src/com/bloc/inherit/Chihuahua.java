package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

public class Chihuahua extends Dog {   
    @Override
    public void feed () {
        mWeight = this.getWeight() + WEIGHT_GAIN; //takes current weight and adds .25 each time 
        String[] sizes = new String[] {"tiny", "small", "average", "large"}; 
        List<String> sizeList = Arrays.asList(sizes);
        int index = sizeList.indexOf(mSize); //gets current position in array of size of dog
        mMeals++;
        /*Used the following to print variables and track program
         *System.out.println("pre-evaluation of mMeals and Index = " + index + "mMeals = " + mMeals);
         */
        if (mMeals >= 5) {
            index++;
            mMeals = 0;
        }
        int newSize = index;
        switch (newSize) {
            case 0:
                this.mSize = "tiny";
                System.out.println("Your dog's current size is tiny");
                break;
            case 1:
                this.mSize = "small";
                System.out.println("Your dog's current size is small");
                break;
            case 2:
                this.mSize = "average";
                System.out.println("Your dog's current size is average");
                break;
            case 3:
                this.mSize = "large";
                System.out.println("Your dog's current size is large");
                break;    
        }
        /*Used the following to print variables and track program
         *System.out.println("index = " + index + "newSize = " + newSize +
         *   " mSize = " + mSize + " mMeals = " + mMeals + " mWeight = " + mWeight);
         */
    } 
}