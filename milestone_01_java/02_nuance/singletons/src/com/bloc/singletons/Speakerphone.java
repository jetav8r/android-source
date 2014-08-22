package com.bloc.singletons;

import java.util.*;

/*
 * This is a singleton class which maintains communication
 * between classes and Listeners
 */
public class Speakerphone extends Object {
	/*
	 * get
	 * @return the singleton instance of Speakerphone
	 */
    private static Speakerphone sSphone;
    
    public static Object getInstance() {
        if (sSphone == null) {
            sSphone = new Speakerphone();
        }
        return sSphone;
    }
    
    private HashSet<Listener> mListener;
    
    private Speakerphone() {
        mListener = new HashSet<>();
    }
	/*
	 * addListener
	 * Add a listener to Speakerphone's list
	 * @param listener an instance of the Listener interface
	 */
    public void addListener(Listener listener) {
        mListener.add(listener);
    }
	/*
	 * removeListener
	 * Remove a Listener from the Speakerphone's list
	 * @param listener the Listener to remove
	 */
    public void removeListener(Listener listener) {
        mListener.remove(listener);
    }
	/*
	 * shoutMessage
	 * Sends the message to all of the Listeners tracked by Speakerphone
	 * @param talker a Talker whose message will be sent
	 */
    public void shoutMessage(Talker talker) {
       Iterator<Listener> shoutIterator = mListener.iterator();
       while (shoutIterator.hasNext()) {
           shoutIterator.next().talker.getMessage();
       }
    }

	/*
	 * shoutMessage
	 * Sends the message to all of the Listeners which are instances of
	 * the class parameter
	 * @param talker a Talker whose message will be sent
	 * @param cls a Class object representing the type which the Listener
	 *			  should extend from in order to receive the message
	 *
	 * HINT: see Class.isAssignableFrom()
	 *		 http://docs.oracle.com/javase/7/docs/api/java/lang/Class.html#isAssignableFrom(java.lang.Class)
	 */

	/*
	 * removeAll
	 * Removes all Listeners from Speakerphone
	 */
    public void removeAll() {
        this.mListener.clear();
    }

}