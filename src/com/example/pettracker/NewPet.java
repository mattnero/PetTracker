package com.example.pettracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NewPet extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_pet_screen);
	    //THIS IS A TEST TO SEE IF I HAVE GITHUB WORKING!!!!!
	}
	
	
}

