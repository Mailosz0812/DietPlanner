package org.locations.dietplanner.Implementation.mealBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.IngredientType;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@JsonTypeName("Meal")
public class Meal implements IMealsGroup, IMeal, Serializable {
    private Recipe recipe;
    private LocalDate day;

    public Meal() {
    }
    Meal(Recipe recipe,LocalDate day){
        this.day = day;
        this.recipe = recipe;

    }
    @Override
    public Double calculateCalories() {
        return recipe.calculateCalories();
    }

    @Override
    public Double calculateFat() {
        return recipe.calculateFat();
    }

    @Override
    public Double calculateCarb() {
        return recipe.calculateCarb();
    }

    @Override
    public Double calculateProtein() {
        return recipe.calculateProtein();
    }

    @Override
    public HashMap<IngredientType, List<Ingredient>> groupIngredients() {
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
    public Recipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;

    }


    @Override
    public void setDay(String date) {
        this.day = LocalDate.parse(date);

    }

    @Override
    public LocalDate getDay() {
        return this.day;
    }

    @Override
    public List<IMeal> getMeal() {
        List<IMeal> mealList = new ArrayList<>();
        mealList.add(this);
        return mealList;
    }

    @Override
    public List<IMeal> getMealByDate(LocalDate date) {
        List<IMeal> mealList = new ArrayList<>();
        if(this.day.equals(date)){
            mealList.add(this);
        }
        return mealList;
    }

    @Override
    public List<String> toStringGroups() {
        List<String> stringList = new ArrayList<>();
        StringBuilder string = new StringBuilder();
        string.append(this.recipe.toString());
        string.append(this.day.toString());
        stringList.add(string.toString());
        return stringList;
    }
}
