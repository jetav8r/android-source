package com.bloc.singletons;

import com.bloc.singletons.listeners.*;
import com.bloc.singletons.talkers.*;
import com.bloc.singletons.Speakerphone;

public class Main extends Object {
    public static void main(String [] args) {
		// Instantiate some talkers and some listeners
            Parent mom = new Parent();
            PetOwner jack = new PetOwner();
            Singer whitney = new Singer();
            AudienceMember audience = new AudienceMember();
            Child boy = new Child();
            Pet dog = new Pet();
            	// Register listeners
            com.bloc.singletons.Speakerphone.addListener(audience);
		// Send messages!
            //send talker message....i.e.mom.getMessage()
	}
}
