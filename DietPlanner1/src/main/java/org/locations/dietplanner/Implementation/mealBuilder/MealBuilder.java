package org.locations.dietplanner.Implementation.mealBuilder;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeBuilder;
import org.locations.dietplanner.Implementation.Builder.RecipeStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class MealBuilder {
    private Recipe recipe;
    private LocalDate day;
    private RecipeBuilder builder;
    private RecipeStorage storage;

    public MealBuilder(RecipeBuilder builder){
        this.builder = builder;
    }

    public MealBuilder addIngredients(List<Ingredient> ingredientList){
        this.builder.addIngredients(ingredientList);
        return this;
    }
    public MealBuilder addIngredient(Ingredient ingredient){
        this.builder.addIngredient(ingredient);
        return this;
    }
    public MealBuilder addMealType(String mealType){
        this.builder.addRecipeType(mealType);
        return this;
    }
    public MealBuilder addRecipeText(String recipeText){
        this.builder.addRecipeText(recipeText);
        return this;
    }
    public MealBuilder addRecipeName(String name){
        this.builder.addRecipeName(name);
        return this;

    }
    public MealBuilder setDate(LocalDate date){
        this.day = date;
        return this;
    }
    public Meal build(){
        this.recipe = builder.Build();
        return new Meal(this.recipe,this.day);

    }
}
