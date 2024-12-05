package org.locations.dietplanner.Implementation.factoryMeal;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.dinner;
import org.locations.dietplanner.Interfaces.IMeal;

import java.util.List;

public class dinnerFactory extends MealFactory{
    @Override
    public IMeal createMeal(List<Double> calories, List<Double> fats, List<Double> carbs, List<Double> proteins
            , String name) {
        dinner dinner = new dinner(recipe);
        dinner.setRecipe(recipe);
        return dinner;
    }
}
