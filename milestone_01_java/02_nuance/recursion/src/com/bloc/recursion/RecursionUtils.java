package com.bloc.recursion;

import java.util.*;

public class RecursionUtils extends Object {
	/*
	 * findMaxRecursively
	 * Takes a list of numbers and finds the largest among them
	 * using recursive calls.
	 *
	 * @param numbers a list of numbers, can be odd or even numbered
	 * @return the largest number in the list
	 *
	 * Hint: your base case may be a comparison of 2 numbers
	 */
    public static final int findMaxRecursively(List<Integer> numbers) {
		// IMPLEMENT ME
        System.out.println(numbers);
        System.out.println(numbers.size());
        int max = 0;
        int i = 0;
        while (i <= numbers.size() - 1) {
            if (numbers.get(i) > max) {
                //do recursive stuff here
                max = numbers.get(i);
                System.out.print("Value of index at i: = " + numbers.get(i));
                System.out.print(", max = " + max);
                System.out.println(", i = " + i);
            }
            i++; 
        }
        //call method with i + 1;
	return max;
    }
}