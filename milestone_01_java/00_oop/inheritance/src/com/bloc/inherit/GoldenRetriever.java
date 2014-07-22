package com.bloc.inherit;

import java.util.Arrays;
import java.util.List;

class GoldenRetriever extends Dog {   
    @Override
    void play () {
        float newWeight = this.getWeight() - (mPlays * WEIGHT_LOSS);
        float minWeight = this.MIN_WEIGHT;
        if (newWeight < minWeight) {
            newWeight = this.MIN_WEIGHT;
        }
        int sizeDecrement = (int) Math.floor(mPlays/3);
        String[] sizes = new String[] {"tiny", "small", "average", "large"}; 
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
        }
    }
}