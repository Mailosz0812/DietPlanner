package org.locations.dietplanner.Implementation.Builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class RecipeStorage implements Serializable {
    private static RecipeStorage service;
    private List<Recipe> recipeList = new ArrayList<>();

    private RecipeStorage(){}
    public static RecipeStorage getInstance(){
        if(service == null){
            service = new RecipeStorage();
        }
        return service;
    }
    public Recipe getRecipe(String name){
        for (Recipe recipe : recipeList) {
            if(recipe.getName().equals(name)){
                return recipe;
            }
        }
        throw new NoSuchElementException("No such recipe in storage");
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
    public List<Recipe> getRecipeList(){
        return this.recipeList;
    }
}
