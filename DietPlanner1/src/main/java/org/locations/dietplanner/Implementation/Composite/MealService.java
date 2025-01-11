package org.locations.dietplanner.Implementation.Composite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.IngredientType;
import org.locations.dietplanner.Implementation.mealBuilder.Meal;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@JsonTypeName("MealService")
public class MealService implements IMealsGroup, Serializable {
    @JsonProperty("MealsGroup")
    private List<IMealsGroup> MealsGroup;
    private LocalDate startDate;
    private LocalDate endDate;

    @JsonCreator
    public MealService(@JsonProperty("startDate") String startDate,@JsonProperty("endDate")String endDate){
        this.MealsGroup = new ArrayList<>();
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }
    public MealService(LocalDate startDate,LocalDate endDate){
        this.MealsGroup = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void addMealGroup(IMealsGroup mealsGroup,LocalDate date){
        if(isDateWithinRange(date, startDate, endDate)){
            this.MealsGroup.add(mealsGroup);
        }else{
            MealService existingService = findExistingMealService(date);
            if(existingService == null){
                LocalDate weekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate weekEnd = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                MealService newService = new MealService(weekStart, weekEnd);
                newService.MealsGroup.add(mealsGroup);
                this.MealsGroup.add(newService);
            }else{
                existingService.MealsGroup.add(mealsGroup);
            }
        }
    }

    private boolean isDateWithinRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return (date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate));
    }

    public void removeMealGroup(IMealsGroup mealsGroup){
        MealsGroup.remove(mealsGroup);
    }
    private MealService findExistingMealService(LocalDate date) {
        for (IMealsGroup group : MealsGroup) {
            if (group instanceof MealService) {
                MealService service = (MealService) group;
                if (isDateWithinRange(date, service.startDate, service.endDate)) {
                    return service;
                }
            }
        }
        return null;
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

    @Override
    public List<String> toStringGroups() {
        List<String> toStringGroups = new ArrayList<>();
        for (IMealsGroup iMealsGroup : MealsGroup) {
            toStringGroups.addAll(iMealsGroup.toStringGroups());
        }
        return toStringGroups;
    }
}
