package com.bloc.inherit;

class GreatDane extends Dog {
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
                    int sizeValue = 4;
                    break;
                case "average":
                    int sizeValue = 7;
                    break;
                case "large":
                    int sizeValue = 10;
                    break;
                case "huge":
                    int sizeValue = 13;
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
                        if (newSize <13 && newSize >=10) { 
                        newDogSize = "large";
                        } else {
                            newDogSize = "huge";
                        }
                    }
                }
            }
        }
}