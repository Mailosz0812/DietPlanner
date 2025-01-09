package org.locations.dietplanner.Implementation;


import java.util.Arrays;
import java.util.List;

public enum IngredientType {
    FRUIT,
    VEGETABLES,
    MEAT,
    DAIRY,
    DRINKS,
    SWEETS,
    HERBS,
    GRAIN,
    OTHER;
    public static IngredientType getIngredientType(String type){
        try{
            return IngredientType.valueOf(type);
        }catch(IllegalArgumentException e){
            System.out.println("Incorrect ingredientType");
            return null;
        }
    }
    public static List<IngredientType> getTypes(){
        return Arrays.asList(IngredientType.values());
    }
}
