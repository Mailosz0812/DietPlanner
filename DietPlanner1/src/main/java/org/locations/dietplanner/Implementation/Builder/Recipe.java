package org.locations.dietplanner.Implementation.Builder;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.locations.dietplanner.Implementation.MealType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@JsonTypeName("org.locations.dietplanner.Implementation.Builder.Recipe")
public class Recipe implements Serializable {
    private List<Ingredient> ingredientList;
    private String recipeText;
    private MealType mealType;
    private String name;

    public Recipe(){
    }
    public Recipe(List<Ingredient> ingredientList,String recipeText,MealType mealType,String name){
        this.recipeText = recipeText;
        this.ingredientList = ingredientList;
        this.mealType = mealType;
        this.name = name;
    }

    public List<Ingredient> getIngredientList(){
        return this.ingredientList;
    }

    public String getName(){
        return this.name;
    }

    public Double calculateCalories(){
        Double calories = 0.0;
        for (Ingredient ingredient : ingredientList) {
            calories+=ingredient.getCalories();
        }
        return calories;
    }

    public Double calculateFat(){
        Double fat = 0.0;
        for (Ingredient ingredient : ingredientList) {
            fat+=ingredient.getFat();
        }
        return fat;
    }

    public Double calculateProtein(){
        Double protein = 0.0;
        for (Ingredient ingredient : ingredientList) {
            protein+=ingredient.getProtein();
        }
        return protein;
    }

    public Double calculateCarb(){
        Double carb = 0.0;
        for (Ingredient ingredient : ingredientList) {
            carb+=ingredient.getCarb();
        }
        return carb;
    }
    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getRecipeText() {
        return recipeText;
    }
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append(this.name);
        for (Ingredient ingredient : ingredientList) {
            string.append(ingredient.toString());
        }
        string.append(this.mealType.toString());
        return string.toString();
    }
}
