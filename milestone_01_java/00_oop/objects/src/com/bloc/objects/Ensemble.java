package com.bloc.objects;

class Ensemble extends Object {
	// Name
	String mName;

	// All of the artists in the group
	Artist[] mArtists;

	/*
	 * First Constructor
	 * This constructor takes in a variable length of Artist objects
	 * @param artists variable length artists
	 */
	// CONSTRUCTOR CODE GOES HERE
        Ensemble (Artist[] artists) {
            mArtists= artists;
        }
     
	/*
	 * Second Constructor
	 * This constructor takes a name and a variable length of Artist objects
	 * Side-effect: if the 'name' parameter is null, uses the first and
	 * 				last name of the first Artist
	 * Hint:		You can add Strings together with a '+'
	 * @param name the name of the group
	 * @param artists variable length artists
	 */
	// CONSTRUCTOR CODE GOES HERE
        Ensemble (String name, Artist[] artists) {
            mName = name;
            mArtists = artists;
            if (name == null) {
                mName = artists[0].getFirstName() + " " + artists[0].getLastName();
            }
        }
}