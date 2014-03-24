package com.example.pettracker;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.annotation.SuppressLint;
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
    
	
	@SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen1);
        
        LinearLayout ll = (LinearLayout)findViewById(R.id.newbuttons);
        DatabaseHandler db = new DatabaseHandler(this);
        List<PetInfo> petlist = db.getAllPets();
        
        int petcount = db.getPetCount();
        for(int i=0;i < petcount;i++){
        	
        	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        	
        	
        	Button newpet = new Button(this);
        	newpet.setId(petlist.get(i).getID());
        	newpet.setText(petlist.get(i).getName());
        	
        	ll.addView(newpet, params);
        	
        	
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
