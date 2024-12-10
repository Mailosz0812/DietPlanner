package org.locations.dietplanner.Implementation.factoryMeal;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeBuilder;
import org.locations.dietplanner.Implementation.breakfast;
import org.locations.dietplanner.Interfaces.IMeal;

import java.util.ArrayList;
import java.util.List;

public class breakfastFactory extends MealFactory{
    public IMeal createMeal(List<Double> calories, List<Double> fats, List<Double> carbs, List<Double> proteins
            , List<String> names,List<String> types,String recipeText,String dateOfMeal,String mealType) {
        Recipe recipe = super.prepareMeal(calories, fats, carbs, proteins, names, types, recipeText,mealType);
        return new breakfast(recipe,dateOfMeal);
    }
}
