package org.locations.dietplanner.Implementation.Builder;

public class Ingredient {
    private Double calories;
    private Double fat;
    private Double carb;
    private Double protein;
    private String type;
    private String name;

    public Ingredient(Double calories,Double fat,Double carb,Double protein,
    String type,String name){
        this.calories = calories;
        this.fat = fat;
        this.name = name;
        this.carb = carb;
        this.protein = protein;
        this.type = type;
    }
}
