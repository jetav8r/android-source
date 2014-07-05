package com.bloc.inherit;

class Chihuahua extends Dog {
    @Override
    void feed(String newDogSize) {
            int meals;
            int newSize;
            //added newSize variable, since no weights for dogs were defined
            //and every 3 meals changed the size of the dog, regardless of actual weight gain
            float newWeight = mWeight + (WEIGHT_GAINED_FROM_FEEDING * meals);
            switch(mSize) {
                case "tiny":
                    int sizeValue = 1;
                    break;
                case "small":
                    int sizeValue = 6;
                    break;
                case "average":
                    int sizeValue = 11;
                    break;
                case "large":
                    int sizeValue = 16;
                    break;
                newSize = sizeValue + meals;               
                }
            if (newSize < 6) {
                newDogSize = "tiny";
                } else {
                if (newSize < 11 && newSize >= 6) {
                    newDogSize = "small";
                } else {
                    if (newSize <16 && newSize >= 11) {
                        newDogSize = "average";
                        } else {
                        newDogSize = "large";
                    }
                }
            }
        }
} 

    