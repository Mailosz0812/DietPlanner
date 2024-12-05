package org.locations.dietplanner.Implementation.factoryMeal;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.supper;
import org.locations.dietplanner.Interfaces.IMeal;

import java.util.List;

public class supperFactory extends MealFactory{
    @Override
    public IMeal createMeal( List<Double> calories, List<Double> fats, List<Double> carbs, List<Double> proteins
            , String name) {
        supper supper = new supper(recipe);
        return supper;
    }
}
