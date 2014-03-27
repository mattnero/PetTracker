package com.example.pettracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
	    Cursor cursor = context.getContentResolver().query(photoUri,
	            new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
	            null, null, null);

	    try {
	        if (cursor.moveToFirst()) {
	            return cursor.getInt(0);
	        } else {
	            return -1;
	        }
	    } finally {
	        cursor.close();
	    }
	}
	
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    

	    //Detects request codes
	    if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
	        Uri selectedImage = data.getData();
	         
	        //int orientation = getOrientation(NewPet.this,selectedImage);
	        File filepath = new File(getRealPathFromURI(selectedImage));
	        String imagepath = filepath.toString();
	        
	        ExifInterface exif = null;
			try {
				exif = new ExifInterface(imagepath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
	        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
	        
	        Bitmap bitmap = null;
	        
	        try {
	                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
	                Bitmap bmRotated = rotateBitmap(decodeSampledBitmapFromFile(imagepath, 200, 200), orientation); 
	                addpic.setImageBitmap(bmRotated);
	                addpic.setScaleType(ScaleType.CENTER);
	        } catch (FileNotFoundException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	        } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	        }
	    }
	}
	
	
	
	
	private String getRealPathFromURI(Uri contentURI) {
		Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			return contentURI.getPath();
		} else { 
		        cursor.moveToFirst(); 
		        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
		        return cursor.getString(idx); 
		}
	}
	 
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
	
	public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filename, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filename, options);
	}
	
	
	public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

	    Matrix matrix = new Matrix();
		switch (orientation) {
		    case ExifInterface.ORIENTATION_NORMAL:
		        return bitmap;
		    case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
		        matrix.setScale(-1, 1);
		        break;
		    case ExifInterface.ORIENTATION_ROTATE_180:
		        matrix.setRotate(180);
		        break;
		    case ExifInterface.ORIENTATION_FLIP_VERTICAL:
		        matrix.setRotate(180);
		        matrix.postScale(-1, 1);
		        break;
		    case ExifInterface.ORIENTATION_TRANSPOSE:
		        matrix.setRotate(90);
		        matrix.postScale(-1, 1);
		        break;
		   case ExifInterface.ORIENTATION_ROTATE_90:
		       matrix.setRotate(90);
		       break;
		   case ExifInterface.ORIENTATION_TRANSVERSE:
		       matrix.setRotate(-90);
		       matrix.postScale(-1, 1);
		       break;
		   case ExifInterface.ORIENTATION_ROTATE_270:
		       matrix.setRotate(-90);
		       break;
		   default:
		       return bitmap;
		}
		try {
		    Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		    bitmap.recycle();
		    return bmRotated;
		}
		catch (OutOfMemoryError e) {
		    e.printStackTrace();
		    return null;
		}
	}
	
	
}

