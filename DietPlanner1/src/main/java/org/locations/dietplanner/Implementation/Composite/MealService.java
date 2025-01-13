package org.locations.dietplanner.Implementation.Composite;

import com.fasterxml.jackson.annotation.*;
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

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonTypeName("MealService")
public class MealService implements IMealsGroup, Serializable {
    @JsonProperty("MealsGroup")
    private List<IMealsGroup> MealsGroup = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;

    public MealService(){

    }
    public MealService(LocalDate startDate,LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void addMealGroup(Meal mealsGroup){
        this.MealsGroup.add(mealsGroup);
    }

    public MealService getMealServiceByDate(LocalDate date){
        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        MealService service = null;
        for (IMealsGroup mealsGroup : MealsGroup) {
            if(mealsGroup instanceof MealService){
                if(((MealService) mealsGroup).isDateWithinRange(date,((MealService) mealsGroup).startDate,((MealService) mealsGroup).endDate)){
                    service = (MealService) mealsGroup;
                    break;
                }
            }
        }
        if(service == null){
            service = new MealService(start,end);
            this.MealsGroup.add(service);
        }
        return service;
    }

    public void removeMealsGroup(IMealsGroup mealsGroup){
        this.MealsGroup.remove(mealsGroup);
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isDateWithinRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return (date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate));
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
    @JsonIgnore
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
            toStringGroups.add("\n");
        }
        return toStringGroups;
    }
}
