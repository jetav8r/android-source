package com.bloc.generics;

import com.bloc.generics.things.*;

public class Main extends Object {

	public static void main(String [] args) {

		ToyBox toyBox = new ToyBox();
		/*
		 * Put a bunch of Toys in toyBox!
		 */
                toyBox.addToy(new Toy("Book about confusing generic types"));
                toyBox.addToy(new Toy("Spoon"));
                toyBox.addToy(new Toy("Action Figure"));
                toyBox.addToy(new Toy("Can I Put Anything in here?"));
                //above added Strings to toy box, but not objects (I think)
                //what about adding the objects Book, Spoon, ActionFigure?
                Book book = new Book();
                toyBox.addToy(new Toy(book));
                //the above worked, but is it the correct way to do this??
                

		assert toyBox.getToyCount() > 0 : "Let's get some toys in that box!";
		System.out.println("Inside your toybox you've got:");
		for (int i = 0; i < toyBox.getToyCount(); i++) {
			System.out.println("- " + toyBox.getToyAtIndex(i).get());                        
		}
                //added line to print toy count
                //System.out.println("ToyCount = " + toyBox.getToyCount());
		System.out.println("Sounds like fun!\n");

		System.out.println("/************************/");
		System.out.println("/*                      */");
		System.out.println("/*                      */");
		System.out.println("/*   If you see this,   */");
		System.out.println("/*   congratulations!   */");
		System.out.println("/*                      */");
		System.out.println("/*                      */");
		System.out.println("/************************/\n");
	}
}
