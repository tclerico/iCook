package edu.ithaca.goosewillis.iCook;

public class Ingredient {

    public String name;
    public double count;
    public double cookTime;  //time for ingredient to cook for one-tray-meal at 400 degrees
    // will add appropriate tags (Vegan, Vegetarian, etc.) after API stuff

    public Ingredient(){}

    public Ingredient(String name, double count, double cookTime){
        if(count>0 && cookTime>0 && !name.equals("") && !name.equals(" ")){
            this.name=name;
            this.count=count;
            this.cookTime=cookTime;
        }else{
            throw new IllegalArgumentException("Please enter valid ingredient inputs.");
        }
    }

    public String getName(){return name;}

    public double getCount(){return count;}

    public double getCookTime(){return cookTime;}

    public void setName(String name){this.name=name;}

    public void setCount(double count){this.count=count;}

    public void setCookTime(double cookTime){this.cookTime=cookTime;}
}
