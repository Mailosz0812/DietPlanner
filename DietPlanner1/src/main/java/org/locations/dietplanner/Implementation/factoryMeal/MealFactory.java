package org.locations.dietplanner.Implementation.factoryMeal;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeBuilder;
import org.locations.dietplanner.Interfaces.IMeal;

import java.util.List;

public abstract class MealFactory{
    protected Recipe prepareMeal(List<Double> calories,List<Double> fats,List<Double> carbs,List<Double> proteins
    ,List<String> names,List<String> types,String recipeText,String mealType){
        if(calories.size() != fats.size() && fats.size() != carbs.size()
                && carbs.size() != proteins.size() && proteins.size() != names.size() && names.size() != types.size()){
            throw new IllegalArgumentException("Invalid parameter");
        }
        RecipeBuilder builder = new RecipeBuilder();
        for (int i = 0; i < calories.size(); i++) {
            Ingredient ingredient = new Ingredient(calories.get(i),
                    fats.get(i),carbs.get(i),proteins.get(i), types.get(i), names.get(i));
            builder.addIngredient(ingredient);
        }
        builder.addRecipeType(mealType);
        if(recipeText != null){
            builder.addRecipeText(recipeText);
        }
        return builder.Build();
    }
}
