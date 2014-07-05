package com.bloc.inherit;

class GoldenRetriever extends Dog {
    @Override
    void play(String newDogSize) {
            int newSize;
            int plays;
            //added newSize variable, since no weights for dogs were defined
            //and every 6 plays changed the size of the dog, regardless of actual weight loss
            float newWeight = mWeight - (WEIGHT_LOST_FROM_FEEDING * plays);
            if (newWeight < MINIMUM_WEIGHT) {
                newWeight = 1;
            }
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
                newSize = sizeValue - plays;
            }
            if (newSize < 4) {
                newDogSize = "tiny";
                } else {
                if (newSize >= 4 && newSize < 7) {
                    newDogSize = "small";
                } else {
                    if (newSize >=7 && newSize < 10) {
                        newDogSize = "average";
                        } else {
                        if (newSize >= 10) {
                        newDogSize = "large";
                        }
                    }
                }
            if (newSize < 1) {
                newSize = 1;
            }
            }
            }
    
}
// CLASS DEFINITION GOES HERE