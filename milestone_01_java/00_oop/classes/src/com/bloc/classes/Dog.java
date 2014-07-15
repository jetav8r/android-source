package com.bloc.classes;

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

	/*
	 * getHairLength
	 * @return this Dog's hair length
	 */
        // ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        float getHairLength() {           
            return mHairLength;
        }

	/*
	 * setHairLength
	 * Sets the length of the Dog's hair
	 * @param hairLength the new length of the hair, a float
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        void setHairLength(float hairLength) {
            mHairLength = hairLength;
        }

	/*
	 * getGender
	 * @return this Dog's gender
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        String getGender() {
            return mGender;
        }

	/*
	 * setGender
	 * Sets this Dog's gender
	 * @param gender the new gender of the Dog, a String
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        void setGender(String gender) {
            mGender = gender;
        }

	/*
	 * getSize
	 * @return the size of the dog
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        String getSize() {
            return mSize;
        }

	/*
	 * setSize
	 * Sets the size of the Dog
	 * @param size the new size of the Dog, a String
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        void setSize(String size) {
            mSize = size;
        }

	/*
	 * getAge
	 * @return this Dog's age
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        int getAge() {
            return mAge;
        }

	/*
	 * setAge
	 * Sets the age of the Dog
	 * @param age the new age of the Dog, an int
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        void int setAge(int age){
            mAge = age;
        }

	/*
	 * getWeight
	 * @return this Dog's weight
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        float getWeight() {
            return mWeight;
        }

	/*
	 * setWeight
	 * Sets the weight of the Dog
	 * @param weight the new weight of the Dog, a float
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        void setWeight(float weight) {
            mWeight = weight;
        }

	/*
	 * getColor
	 * @return this Dog's color
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        String getColor() {
            return mColor;
        }

	/*
	 * setColor
	 * Sets the color of the Dog
	 * @param color the new color of the Dog's coat, a String
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        String setColor(String color) {
            mColor = color;
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
        
        void feed(String newDogSize) {
            int meals;
            int newSize;
            //added newSize variable, since no weights for dogs were defined
            //and every 3 meals changed the size of the dog, regardless of actual weight gain
            float newWeight = mWeight + (WEIGHT_GAIN * meals);
            switch(mSize) {
                case "tiny":
                    int sizeValue = 1;
                    break;
                case "small":
                    int sizeValue = 4;
                    break;
                case "average":
                    int sizeValue = 7;
                    break;
                case "large":
                    int sizeValue = 10;
                    break;
                newSize = sizeValue + meals;               
                }
            if (newSize < 4) {
                newDogSize = "tiny";
                } else {
                if (newSize < 7 && newSize >= 4) {
                    newDogSize = "small";
                } else {
                    if (newSize <10 && newSize >= 7) {
                        newDogSize = "average";
                        } else {
                        newDogSize = "large";
                    }
                }
            }
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
 
        void play(String newDogSize) {
            int newSize;
            int plays;
            //added newSize variable, since no weights for dogs were defined
            //and every 6 plays changed the size of the dog, regardless of actual weight loss
            float newWeight = mWeight - (WEIGHT_LOSS * plays);
            if (newWeight < MIN_WEIGHT) {
                newWeight = 1.25;
            }
            switch(mSize) {
                case "tiny":
                    int sizeValue = 1;
                    break;
                case "small":
                    int sizeValue = 7;
                    break;
                case "average":
                    int sizeValue = 13;
                    break;
                case "large":
                    int sizeValue = 19;
                    break;
                newSize = sizeValue - plays;               
                }
            if (newSize < 7) {
                newDogSize = "tiny";
                } else {
                if (newSize >= 7 && newSize < 13) {
                    newDogSize = "small";
                } else {
                    if (newSize >=13 && newSize < 19) {
                        newDogSize = "average";
                        } else {
                        if (newSize >= 19) {
                        newDogSize = "large";
                        }
                    }
                }
            }
        }        
        

	/*
	 * cutHair
	 * Side-effect: the Dog's hair length is reduced by HAIR_CUT_LENGTH
     * The Dog's hair cannot be shorter than 0f
	 * @return nothing
	 */
	// ADD YOUR METHOD HERE, NAME MUST MATCH DESCRIPTION
        
        void float cutHair(float newHairLength) {
            float newLength = mHairLength - HAIR_CUT_LENGTH);
            float newHairLength = (newLength < 0) ? 0 : newLength;
        }       
}