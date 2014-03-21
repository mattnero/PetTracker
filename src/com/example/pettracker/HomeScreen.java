package com.example.pettracker;

import android.os.Bundle;
import android.view.View;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeScreen extends Activity {
	
	ImageButton addpet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        
        Bundle extras = getIntent().getExtras(); 
        String petname;

        if (extras != null) {
        	
            petname = extras.getString("petname");
            if (extras.getBoolean("newicon")){
            	
            	Button newpet = new Button(this);
            	newpet.setText(petname);
            	RelativeLayout layout = (RelativeLayout) findViewById(R.id.newbuttons);
            	layout.addView(newpet); 
            	
            }
        }
       
        addpet = (ImageButton)findViewById(R.id.imageButton1);
        
        addpet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent newintent = new Intent(HomeScreen.this, NewPet.class);
				
                startActivity(newintent);      
                
				
				
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }
    
    protected void onPause(){
    	super.onPause();
    	
    	
    }
   
   

    
}
