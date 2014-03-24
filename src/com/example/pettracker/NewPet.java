package com.example.pettracker;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;

public class NewPet extends Activity{
	ImageButton addpic;
	Button save; 
	Button cancel;
	String petname;
	int pettype;
	int petweight;
	EditText name;
	EditText type;
	EditText weight;
	DatabaseHandler db;
	public static final int GET_FROM_GALLERY = 3;
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_pet_screen);
	    
	    db = new DatabaseHandler(this);
	    name = (EditText)findViewById(R.id.editText1) ;
	    type = (EditText)findViewById(R.id.editText2) ;
	    weight = (EditText)findViewById(R.id.editText3) ;
		
	    
	    save = (Button)findViewById(R.id.button1);
	    cancel = (Button)findViewById(R.id.button2);
	    addpic = (ImageButton)findViewById(R.id.selectImage);
        
	    save.setOnClickListener(onClickListener);
	    cancel.setOnClickListener(onClickListener);
        addpic.setOnClickListener(onClickListener);
        
	    
	}
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.button1:
					petname = name.getText().toString();
					pettype = Integer.parseInt(type.getText().toString());
					petweight = Integer.parseInt(weight.getText().toString());
					
					db.addPet(new PetInfo(petname,pettype,petweight));
					
					Intent intent = new Intent(NewPet.this, HomeScreen.class);
	                startActivity(intent);      
	                finish();
				break;
				case R.id.button2:
					Intent intent2 = new Intent(NewPet.this, HomeScreen.class);
	                startActivity(intent2);      
	                finish();
				break;
				
				case R.id.selectImage:
					
					startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
				break;
			
			
			
			}
		}
	};
	public static int getOrientation(Context context, Uri photoUri) {
	    /* it's on the external media. */
	    Cursor cursor = context.getContentResolver().query(photoUri,
	            new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

	    if (cursor.getCount() != 1) {
	        return -1;
	    }

	    cursor.moveToFirst();
	    return cursor.getInt(0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    

	    //Detects request codes
	    if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
	        Uri selectedImage = data.getData();
	        Bitmap bitmap = null;
	        
	        try {
	                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
	                System.out.println("gets to pic assignment");
	                addpic.setImageBitmap(bitmap);
	                addpic.setScaleType(ScaleType.CENTER_INSIDE);
	        } catch (FileNotFoundException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	        } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	        }
	    }
	}
	
	
	
}

