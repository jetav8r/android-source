package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

class GreatDane extends Dog {   
    @Override
    void feed () {
        float newWeight = this.getWeight() + (mMeals * WEIGHT_GAIN);
        int sizeIncrement = (int) Math.floor(mMeals/5);
        String[] sizes = new String[] {"tiny", "small", "average", "large", "huge"}; 
        List<String> sizeList = Arrays.asList(sizes);
        int index = sizeList.indexOf(mSize);
        int newSize = index + sizeIncrement;
        if (newSize > 4) {
            newSize = 4;
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
            case 4:
                System.out.println("Your dog's current size is huge");
                break;
        }
    }
    void play () {
            float newWeight = this.getWeight() - (mPlays * WEIGHT_LOSS);
            float minWeight = this.MIN_WEIGHT;
            if (newWeight < minWeight) {
                newWeight = this.MIN_WEIGHT;
            }
            int sizeDecrement = (int) Math.floor(mPlays/6);
            String[] sizes = new String[] {"tiny", "small", "average", "large", "huge"}; 
            List<String> sizeList = Arrays.asList(sizes);
            int index = sizeList.indexOf(mSize);
            int newSize = index - sizeDecrement;
            if (newSize < 0) {
                newSize = 0;
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
                case 4:
                    System.out.println("Your dog's current size is huge");
                    break;
            }
        }
}