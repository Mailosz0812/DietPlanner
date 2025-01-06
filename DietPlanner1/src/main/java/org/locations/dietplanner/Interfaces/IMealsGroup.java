package org.locations.dietplanner.Interfaces;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.IngredientType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface IMealsGroup {
    Double calculateCalories();
    Double calculateFat();
    Double calculateCarb();
    Double calculateProtein();
    HashMap<IngredientType, List<Ingredient>> groupIngredients();
    List<IMeal> getMeal();
    List<IMeal> getMealByDate(LocalDate date);


}
