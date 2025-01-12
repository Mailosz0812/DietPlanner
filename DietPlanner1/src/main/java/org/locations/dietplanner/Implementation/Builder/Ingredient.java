package org.locations.dietplanner.Implementation.Builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.locations.dietplanner.Implementation.IngredientType;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type" // The type field in the JSON
)
@JsonTypeName("Ingredient")
public class Ingredient implements Serializable {
    private Double calories;
    private Double fat;
    private Double carb;
    private Double protein;
    private IngredientType type;
    private String name;

    public Ingredient(){

    }
    public Ingredient(Double calories,Double fat,Double carb,Double protein,
    String type,String name){
        this.calories = calories;
        this.fat = fat;
        this.name = name;
        this.carb = carb;
        this.protein = protein;
        this.type = IngredientType.getIngredientType(type);
    }
    public Double getCalories(){
        return this.calories;
    }

    public Double getFat() {
        return fat;
    }

    public Double getCarb() {
        return carb;
    }

    public Double getProtein() {
        return protein;
    }

    public IngredientType getType() {
        return type;
    }
    public String getName(){
        return this.name;
    }
    public String toString(){
        return this.name +", "+ this.type;
    }
}
