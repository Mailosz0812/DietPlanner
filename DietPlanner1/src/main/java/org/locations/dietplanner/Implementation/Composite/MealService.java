package org.locations.dietplanner.Implementation.Composite;

import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.IngredientType;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealService implements IMealsGroup, Serializable {
    private List<IMealsGroup> MealsGroup;

    public MealService(){
        this.MealsGroup = new ArrayList<>();

    }
    public void addMealGroup(IMealsGroup mealsGroup){
        MealsGroup.add(mealsGroup);
    }
    public void removeMealGroup(IMealsGroup mealsGroup){
        MealsGroup.remove(mealsGroup);
    }
    @Override
    public Double calculateCalories() {
        Double calories = 0.0;
        for (IMealsGroup iMealsGroup : MealsGroup) {
            calories+= iMealsGroup.calculateCalories();
        }
        return calories;
    }

    @Override
    public Double calculateFat() {
        Double fats = 0.0;
        for (IMealsGroup iMealsGroup : MealsGroup) {
            fats+= iMealsGroup.calculateFat();
        }
        return fats;
    }

    @Override
    public Double calculateCarb() {
        Double carb = 0.0;
        for (IMealsGroup iMealsGroup : MealsGroup) {
            carb+= iMealsGroup.calculateCarb();
        }
        return carb;
    }

    @Override
    public Double calculateProtein() {
        Double protein = 0.0;
        for (IMealsGroup iMealsGroup : MealsGroup) {
            protein += iMealsGroup.calculateProtein();

        }
        return protein;
    }

    @Override
    public HashMap<IngredientType, List<Ingredient>> groupIngredients() {
        HashMap<IngredientType,List<Ingredient>> groupedIngredients = new HashMap<>();
        for (IMealsGroup iMealsGroup : MealsGroup) {
            HashMap<IngredientType,List<Ingredient>> currentIngredientGroup = iMealsGroup.groupIngredients();
            for (Map.Entry<IngredientType, List<Ingredient>> listEntry : currentIngredientGroup.entrySet()) {
                groupedIngredients
                        .computeIfAbsent(listEntry.getKey(),k -> new ArrayList<>())
                        .addAll(listEntry.getValue());
            }
        }
        return groupedIngredients;
    }

    @Override
    public List<IMeal> getMeal() {
        List<IMeal> allMeals = new ArrayList<>();
        for (IMealsGroup iMealsGroup : MealsGroup) {
            allMeals.addAll(iMealsGroup.getMeal());
        }
        return allMeals;
    }
    @Override
    public List<IMeal> getMealByDate(LocalDate date){
        List<IMeal> mealsByDate = new ArrayList<>();
        for (IMealsGroup iMealsGroup : MealsGroup) {
            mealsByDate.addAll(iMealsGroup.getMealByDate(date));
        }
        return mealsByDate;
    }
}
