package org.locations.dietplanner.Implementation;

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
}
