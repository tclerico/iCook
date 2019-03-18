package edu.ithaca.icook;

public class Ingredient {

    public String name;
    public int count;
    // will add appropriate tags (Vegan, Vegetarian, etc.) after API stuff
    // need to discuss with client about ovenTime

    public Ingredient(){}

    public Ingredient(String name, int count){
        if(count>0){
            this.name=name;
            this.count=count;
        }else{
            throw new IllegalArgumentException(count+"is not a valid ingredient count");
        }
    }

    public String getName(){return name;}

    public int getCount(){return count;}

    public void setName(String name){this.name=name;}

    public void setCount(int count){this.count=count;}
}