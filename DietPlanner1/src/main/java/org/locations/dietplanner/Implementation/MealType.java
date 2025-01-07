package org.locations.dietplanner.Implementation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    DESSERT,
    SUPPER;
    @JsonCreator
    public static MealType getEnum(String type){
        try {
            return MealType.valueOf(type.toUpperCase());
        }catch(IllegalArgumentException e){
            System.out.println("Incorrect meal type");
            return null;
        }
    }
    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }
}
