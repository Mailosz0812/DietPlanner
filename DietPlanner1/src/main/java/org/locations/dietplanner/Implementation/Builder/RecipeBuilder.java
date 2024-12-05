package org.locations.dietplanner.Implementation.Builder;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
    private static int nextId = 1;
    private final int id;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private String recipeText;

    public RecipeBuilder(){
        this.id = nextId++;
    }
    public RecipeBuilder addIngredient(Ingredient ingredient){
        this.ingredientList.add(ingredient);
        return this;
    }
    public RecipeBuilder addRecipeText(String recipeText){
        this.recipeText = recipeText;
        return this;
    }
    public Recipe Build(){
        return new Recipe(ingredientList,recipeText);
    }

}
