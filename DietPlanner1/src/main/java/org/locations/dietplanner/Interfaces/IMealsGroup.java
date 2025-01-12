package org.locations.dietplanner.Interfaces;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Composite.MealService;
import org.locations.dietplanner.Implementation.IngredientType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.locations.dietplanner.Implementation.mealBuilder.Meal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MealService.class, name = "MealService"),
        @JsonSubTypes.Type(value = Meal.class, name = "Meal")
})
public interface IMealsGroup {
    Double calculateCalories();
    Double calculateFat();
    Double calculateCarb();
    Double calculateProtein();
    HashMap<IngredientType, List<Ingredient>> groupIngredients();
    List<IMeal> getMeal();
    List<IMeal> getMealByDate(LocalDate date);
    List<String> toStringGroups();


}
