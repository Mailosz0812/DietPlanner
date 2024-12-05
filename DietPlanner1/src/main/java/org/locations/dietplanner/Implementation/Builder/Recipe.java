package org.locations.dietplanner.Implementation.Builder;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private List<Ingredient> ingredientList = new ArrayList<>();
    private String recipeText;
    Recipe(List<Ingredient> ingredientList,String recipeText){
        this.recipeText = recipeText;
        this.ingredientList = ingredientList;
    }

    public Double calculateCalories(){
        return null;
    }

    public Double calculateFat(){
        return null;
    }

    public Double calculateProtein(){
        return null;
    }

    public Double calculateCarb(){
        return null;
    }

}
