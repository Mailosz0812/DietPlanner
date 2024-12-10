package org.locations.dietplanner.Implementation;

public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SUPPER;
    public static MealType getEnum(String type){
        try {
            return MealType.valueOf(type.toUpperCase());
        }catch(IllegalArgumentException e){
            System.out.println("Uncorrect meal type");
            return null;
        }
    }
}
