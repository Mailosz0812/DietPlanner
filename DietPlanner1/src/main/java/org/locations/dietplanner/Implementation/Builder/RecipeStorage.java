package org.locations.dietplanner.Implementation.Builder;

import java.util.ArrayList;
import java.util.List;

public class RecipeStorage {
    private static RecipeStorage service;
    private List<Recipe> recipeList = new ArrayList<>();

    private RecipeStorage(){}
    public static RecipeStorage getInstance(){
        if(service == null){
            service = new RecipeStorage();
        }
        return service;
    }
    public void addRecipe(Recipe recipe){
        recipeList.add(recipe);
    }
    public void editRecipe(Recipe recipe,String recipeText){
        RecipeBuilder recipeBuilder = new RecipeBuilder();
        recipeBuilder.editRecipe(recipe,recipeText);
    }
    public void removeRecipe(Recipe recipe){
        recipeList.remove(recipe);
    }
}
