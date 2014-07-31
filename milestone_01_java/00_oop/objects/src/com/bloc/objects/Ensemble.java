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
            //added code to view variables
            /*System.out.println("Artist constructor variable line....mName = " + 
             *       mName +  ", mArtists = " + mArtists[0] + 
             *       "mFirstName = " + artists[0].getFirstName() + ", mLastName = " +
             *       artists[0].getLastName() + "name = ?");
            //Had to add the following line here, because even though it says it wants 
            //this in the second constructor, Main.java tests it here...grrrrr
            */
            if (mName == null) {
                mName = artists[0].getFirstName() + " " + artists[0].getLastName();
                //System.out.println("mName null Artist variable line....mName = " + 
                //    mName);
            }
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
            //System.out.println("mName = " + mName +  ", mArtists = " + mArtists[0] + 
            //        "mFirstName = " + artists[0].getFirstName() + ", mLastName = " +
            //        artists[0].getLastName());
             
            if (mName == null) {
                mName = artists[0].getFirstName() + " " + artists[0].getLastName();
            //    System.out.println("Null test line----mName = " + mName +  ", mArtists = " + mArtists[0] + 
            //       "mFirstName = " + artists[0].getFirstName() + ", mLastName = " +
            //        artists[0].getLastName() + "name = " + name);
            }
        }
}