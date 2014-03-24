package com.example.pettracker;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "PetManager";
 
    // Contacts table name
    private static final String TABLE_PETS = "pets";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_WEIGHT = "weight";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_PETS + "("
        		+ KEY_ID + " INTEGER PRIMARY KEY,"
                +  KEY_NAME + " TEXT," + KEY_TYPE + " INTEGER,"
                + KEY_WEIGHT + " TEXT" + ")";
        db.execSQL(CREATE_PETS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new pet
    void addPet(PetInfo pet) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, pet.getName()); // pet Name
        values.put(KEY_WEIGHT, pet.getWeight()); // pet weight
        values.put(KEY_TYPE, pet.getType()); //pet type
        
        // Inserting Row
        db.insert(TABLE_PETS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single pet
    PetInfo getPetInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PETS, new String[] { KEY_ID,
                KEY_NAME, KEY_TYPE, KEY_WEIGHT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);  //MIGHT HAVE TO ADD AN EXTRA NULL
        if (cursor != null)
            cursor.moveToFirst();
 
        PetInfo pet = new PetInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        // return pet
        return pet;
    }
     
    // Getting All pets
    public List<PetInfo> getAllPets() {
        List<PetInfo> petList = new ArrayList<PetInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PETS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PetInfo pet = new PetInfo();
                pet.setID(Integer.parseInt(cursor.getString(0)));
                pet.setName(cursor.getString(1));
                pet.setType(cursor.getInt(2));
                pet.setWeight(cursor.getInt(3));
                // Adding contact to list
                petList.add(pet);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return petList;
    }
 
    // Updating single contact
    public int updatePet(PetInfo pet) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, pet.getName());
        values.put(KEY_TYPE, pet.getType());
        values.put(KEY_WEIGHT, pet.getWeight());
 
        // updating row
        return db.update(TABLE_PETS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(pet.getID()) });
    }
 
    // Deleting single contact
    public void deletePet(PetInfo pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PETS, KEY_ID + " = ?",
                new String[] { String.valueOf(pet.getID()) });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getPetCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PETS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
 
        // return count
        return count;
    }
 
}
