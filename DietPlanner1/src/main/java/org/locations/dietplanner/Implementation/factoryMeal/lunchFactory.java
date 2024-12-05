package org.locations.dietplanner.Implementation.factoryMeal;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.lunch;
import org.locations.dietplanner.Interfaces.IMeal;

import java.util.List;

public class lunchFactory extends MealFactory{
    @Override
    public IMeal createMeal(List<Double> calories, List<Double> fats, List<Double> carbs, List<Double> proteins
            , String name) {
        //lunch lunch = new lunch(recipe);
        //return lunch;
    }
}
