package org.locations.dietplanner.Implementation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static List<MealType> getEnumList(){
        return Arrays.asList(MealType.values());
    }
}
