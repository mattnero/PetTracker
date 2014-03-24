package com.example.pettracker;

public class PetInfo {
	 //private variables
	int id;
    int type;
    String name;
    int weight;
     
    // Empty constructor
    public PetInfo(){
         
    }
    // constructor
    public PetInfo(int _id,  String _name,int _type, int _weight){
    	this.id = _id;
        this.type = _type;
        this.name = _name;
        this.weight = _weight;
    }
     
    // constructor
    public PetInfo( String _name,int _type, int _weight){
        this.type = _type;
    	this.name = _name;
        this.weight = _weight;
    }
    
    public int getID(){
        return this.id;
    }
     
    // setting id
    public void setID(int _id){
        this.id = _id;
    }
    
    // getting type
    public int getType(){
        return this.type;
    }
     
    // setting type
    public void setType(int _type){
        this.type = _type;
    }
     
    // getting name
    public String getName(){
        return this.name;
    }
     
    // setting name
    public void setName(String _name){
        this.name = _name;
    }
    public int getWeight(){
        return this.weight;
    }
     
    // setting name
    public void setWeight(int _weight){
        this.weight = _weight;
    }
     
}


