package org.locations.dietplanner.Implementation.Builder;

import org.locations.dietplanner.Implementation.MealType;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
    private static int nextId = 1;
    private final int id;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private String recipeText;
    private MealType mealType;

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
    public RecipeBuilder addRecipeType(String recipeType){
        this.mealType = MealType.getEnum(recipeType);
        return this;

    }
    public void editRecipe(Recipe recipe,String recipeText){
        recipe.setRecipeText(recipeText);
    }
    public Recipe Build(){
        return new Recipe(ingredientList,recipeText,mealType);
    }

}
