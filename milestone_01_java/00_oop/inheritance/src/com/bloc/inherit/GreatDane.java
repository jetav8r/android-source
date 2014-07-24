package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

public class GreatDane extends Dog {
    @Override
    public void feed () {
        mWeight = this.getWeight() + WEIGHT_GAIN; //takes current weight and adds .25 each time 
        String[] sizes = new String[] {"tiny", "small", "average", "large", "huge"}; 
        List<String> sizeList = Arrays.asList(sizes);
        int index = sizeList.indexOf(mSize); //gets current position in array of size of dog
        mMeals++;
        /*Used the following to print variables and track program
         *System.out.println("pre-evaluation of mMeals and Index = " + index + "mMeals = " + mMeals);
         */
        if (mMeals >= 3) {
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
            case 4:
                this.mSize = "huge";
                System.out.println("Your dog's current size is huge");
                break;
        }
        /*Used the following to print variables and track program
         *System.out.println("index = " + index + "newSize = " + newSize +
         *   " mSize = " + mSize + " mMeals = " + mMeals + " mWeight = " + mWeight);
         */
    } 

    /*
     * play
     * Side-effect: 1. The Dog loses weight, specifically WEIGHT_LOSS
     *		2. Every 6 play invocations, the Dog shrinks to a smaller 
     *                 size, if possible
     *                 i.e. "large" (6 plays later->) "average" (6 plays later->) 
     *                 "small" -> "tiny"
     *              3. The Dog cannot shrink to a weight smaller than 
     *                 MIN_WEIGHT
     * @return nothing
     */
    // ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
    @Override
    public void play () {
        mWeight = this.getWeight() - WEIGHT_LOSS; //takes current weight and subtracts .20 each time 
        String[] sizes = new String[] {"tiny", "small", "average", "large", "huge"}; 
        List<String> sizeList = Arrays.asList(sizes);
        int index = sizeList.indexOf(mSize); //gets current position in array of size of dog
        mPlays++;
        /* Used to print variable to follow system logic
         *System.out.println("Index = " + index);
         */
        if (mPlays >= 6) {
            index--;
            mPlays = 0;
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
            case 4:
                this.mSize = "huge";
                System.out.println("Your dog's current size is huge");
                break;
        }
        /*Used the following to print variables and track program
         *System.out.println("index = " + index + "newSize = " + newSize +
         *       " mSize = " + mSize + " mPlays = " + mMeals + " mWeight = " + mWeight);
         */
    }
    /*
     * cutHair
     * Side-effect: the Dog's hair length is reduced by HAIR_CUT_LENGTH
     * The Dog's hair cannot be shorter than 0f
     * @return nothing
     */
    // ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
    public void cutHair () {
        mHairLength = this.getHairLength() - HAIR_CUT_LENGTH;
        if (mHairLength < 0) {
            mHairLength = 0;
        }
    }
}