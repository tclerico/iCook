<<<<<<< HEAD:src/main/java/edu/ithaca/icook/Fridge.java
package edu.ithaca.icook;

import java.util.List;
import java.util.ArrayList;


public class Fridge {

    private List<Ingredient> ingredients;

    public Fridge() {
        this.ingredients = new ArrayList<>();
    }

    public Fridge(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }


}
=======
package edu.ithaca.goosewillis.iCook;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class Fridge {
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    Fridge() {

    }

    Fridge(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    // ignores case
    // returns the ingredient object if it is found
    // null otherwise
    public Ingredient searchIngredient(String name) {
        Iterator<Ingredient> myIterator = this.getIngredients().iterator();
        while (myIterator.hasNext()) {
            Ingredient current = myIterator.next();
            if (current.getName().equalsIgnoreCase(name)) {
                return current;
            }
        }
        return null;
    }

}
>>>>>>> c51903c7b2cfb8bba8cb49029bfaa9421cbd5c99:src/main/java/edu/ithaca/goosewillis/iCook/Fridge.java
