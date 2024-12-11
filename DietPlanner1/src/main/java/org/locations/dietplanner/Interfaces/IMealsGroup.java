package org.locations.dietplanner.Interfaces;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.IngredientType;

import java.util.HashMap;
import java.util.List;

public interface IMealsGroup {
    Double calculateCalories();
    Double calculateFat();
    Double calculateCarb();
    Double calculateProtein();
    HashMap<IngredientType, List<Ingredient>> groupIngredients();
}
