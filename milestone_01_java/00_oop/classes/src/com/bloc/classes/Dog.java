package com.bloc.classes;

import java.util.Arrays;
import java.util.List;

class Dog {
    // The length of hair which
    final float HAIR_CUT_LENGTH = 0.15f;
    // Minimum weight that any Dog can be
    final float MIN_WEIGHT = 1.25f;
	// Amount of weight to gain after eating
	final float WEIGHT_GAIN = 0.25f;
	// Amount of weight to lose after playing
	final float WEIGHT_LOSS = 0.2f;
	// Hair length
	float mHairLength;
	// Gender, either "male" or "female"
	String mGender;
	// Size, either "tiny", "small", "average", or "large"
	String mSize;
	// Its age
	int mAge;
	// Its weight in pounds
	float mWeight;
	// The color of its coat
	String mColor;
        

	// ADD MEMBER VARIABLES HERE IF NECESSARY
        //number of meals that the dog eats
        int mMeals;
        //number of play sessions the dog has
        int mPlays;
        //counter for size increase
        int mSizeIncrement;
       
        
        public int getMeals() {
            return mMeals;
        }
        
        public void setMeals(int pMeals) {
            mMeals = pMeals;
        }
	/*
	 * getHairLength
	 * @return this Dog's hair length
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public float getHairLength() {
            return mHairLength;
        }

	/*
	 * setHairLength
	 * Sets the length of the Dog's hair
	 * @param hairLength the new length of the hair, a float
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void setHairLength(float pHairLength) {
            mHairLength = pHairLength;
        }

	/*
	 * getGender
	 * @return this Dog's gender
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public String getGender() {
            return mGender;
        }

	/*
	 * setGender
	 * Sets this Dog's gender
	 * @param gender the new gender of the Dog, a String
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void setGender(String pGender) {
            mGender = pGender;
        }

	/*
	 * getSize
	 * @return the size of the dog
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public String getSize() {
            return mSize;
        }

	/*
	 * setSize
	 * Sets the size of the Dog
	 * @param size the new size of the Dog, a String
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void setSize(String pSize) {
            mSize = pSize;
        }

	/*
	 * getAge
	 * @return this Dog's age
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public int getAge() {
            return mAge;
        }

	/*
	 * setAge
	 * Sets the age of the Dog
	 * @param age the new age of the Dog, an int
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void setAge(int pAge) {
            mAge = pAge;            
        }

	/*
	 * getWeight
	 * @return this Dog's weight
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public float getWeight() {
            return mWeight;
        }

	/*
	 * setWeight
	 * Sets the weight of the Dog
	 * @param weight the new weight of the Dog, a float
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void setWeight(float pWeight) {
            mWeight = pWeight;
        }

	/*
	 * getColor
	 * @return this Dog's color
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public String getColor() {
            return mColor;
        }

	/*
	 * setColor
	 * Sets the color of the Dog
	 * @param color the new color of the Dog's coat, a String
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void setColor(String pColor) {
            mColor = pColor;
        }

	/*
	 * feed
	 * Side-effect: 1. The Dog gains weight, specifically WEIGHT_GAIN
	 *              2. Every 3 meals, the Dog grows to a larger size, if *                 possible
	 *              i.e. "tiny" (3 meals later ->) "small" (3 meals later ->)
	 *                   "average" (3 meals later ->) "large"
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void feed () {
            mWeight = this.getWeight() + WEIGHT_GAIN; //takes current weight and adds .25 each time 
            String[] sizes = new String[] {"tiny", "small", "average", "large"}; 
            List<String> sizeList = Arrays.asList(sizes);
            int index = sizeList.indexOf(mSize); //gets position in array of size of dog
            mMeals++;
            int sizeIncrement = (int) Math.floor(mMeals/3);
            int newSize = index + sizeIncrement;
            if (newSize > 3) {
                newSize = 3;
            }
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
            System.out.println("index = " + index + " sizeIncrement = " + sizeIncrement +
                    "newSize = " + newSize +
                    " mSize = " + mSize + " mMeals = " + mMeals);
        } 

	/*
	 * play
	 * Side-effect: 1. The Dog loses weight, specifically WEIGHT_LOSS
	 *				2. Every 6 play invocations, the Dog shrinks to a smaller *                 size, if possible
	 *				i.e. "large" (6 plays later->) "average" (6 plays later->) *                   "small" -> "tiny"
     *              3. The Dog cannot shrink to a weight smaller than *                 MIN_WEIGHT
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void play () {
            float newWeight = this.getWeight() - (mPlays * WEIGHT_LOSS);
            float minWeight = this.MIN_WEIGHT;
            if (newWeight < minWeight) {
                newWeight = this.MIN_WEIGHT;
            }
            int sizeDecrement = (int) Math.floor(mPlays/6);
            String[] sizes = new String[] {"tiny", "small", "average", "large"}; 
            List<String> sizeList = Arrays.asList(sizes);
            int index = sizeList.indexOf(mSize);
            int newSize = index - sizeDecrement;
            if (newSize < 0) {
                newSize = 0;
            }
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
        }
	/*
	 * cutHair
	 * Side-effect: the Dog's hair length is reduced by HAIR_CUT_LENGTH
     * The Dog's hair cannot be shorter than 0f
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        public void cutHair () {
            float newLength = this.getHairLength() - HAIR_CUT_LENGTH;
            if (newLength < 0) {
                newLength = 0;
            }
        }
        

}
