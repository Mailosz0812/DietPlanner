package org.locations.dietplanner.Implementation.factoryMeal;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Interfaces.IMeal;

import java.util.List;

public abstract class MealFactory{
    public abstract IMeal createMeal(List<Double> calories,List<Double> fats,List<Double> carbs,List<Double> proteins
    ,List<String> names,List<String> types,String recipeText);
//    private Double calories;
//    private Double fat;
//    private Double carb;
//    private Double protein;
//    private String type;
//    private String name;
}
