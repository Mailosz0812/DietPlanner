package org.locations.dietplanner.Implementation;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class supper implements IMeal, IMealsGroup {
    private Recipe recipe;
    private LocalDate day;

    public supper(Recipe recipe,String date){
        this.recipe = recipe;
        this.day = LocalDate.parse(date);
    }
    @Override
    public HashMap<IngredientType, List<Ingredient>> groupeIngredients() {
        HashMap<IngredientType,List<Ingredient>> ingredientsSet = new HashMap<>();
        List<Ingredient> ingredientList = recipe.getIngredientList();
        for (Ingredient ingredient : ingredientList) {
            ingredientsSet
                    .computeIfAbsent(ingredient.getType(),k -> new ArrayList<>())
                    .add(ingredient);
        }
        return ingredientsSet;
    }
    @Override
    public void setDay(String day) {
        this.day = LocalDate.parse(day);
    }
    @Override
    public String toStringwithDay() {
        return "Supper "+ day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }
    @Override
    public LocalDate getDay() {
        return this.day;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
    @Override
    public Double calculateCalories(){
        return recipe.calculateCalories();
    }

    @Override
    public Double calculateFat(){
        return recipe.calculateFat();
    }

    @Override
    public Double calculateProtein(){
        return recipe.calculateProtein();
    }

    @Override
    public Double calculateCarb(){
        return recipe.calculateCarb();
    }

}
