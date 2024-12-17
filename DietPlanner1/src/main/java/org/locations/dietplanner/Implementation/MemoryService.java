package org.locations.dietplanner.Implementation;

import org.locations.dietplanner.Implementation.Builder.RecipeStorage;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class MemoryService {

    private static MemoryService service;
    private final String recipeFileName = "RecipeFile.ser";
    private final String mealsListFileName = "MealsFile.ser";

    private MemoryService(){}
    public static MemoryService getInstance(){
        if(service == null){
            service = new MemoryService();
        }
        return service;
    }
    public void Serialize(List<IMealsGroup> mealsGroupList,RecipeStorage storage){
        try{
            FileOutputStream file = new FileOutputStream(mealsListFileName);
            FileOutputStream file1 = new FileOutputStream(recipeFileName);

            ObjectOutputStream out = new ObjectOutputStream(file);
            ObjectOutputStream out1 = new ObjectOutputStream(file1);

            out.writeObject(mealsGroupList);
            out1.writeObject(storage);

            out.close();
            out1.close();
            file.close();
            file1.close();

            System.out.println("Meals have been serialized");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<IMealsGroup> deserializeMealsGroup(){
        List<IMealsGroup> deserializedMealsGroupList = null;
        try {
            FileInputStream file = new FileInputStream(mealsListFileName);
            ObjectInputStream in = new ObjectInputStream(file);
            deserializedMealsGroupList = (List<IMealsGroup>) in.readObject();
            file.close();
            in.close();
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
        return deserializedMealsGroupList;
    }
    public RecipeStorage deserializeRecipeStorage(){
        RecipeStorage recipeStorage = null;
        try{
            FileInputStream file = new FileInputStream(recipeFileName);
            ObjectInputStream in = new ObjectInputStream(file);

            recipeStorage = (RecipeStorage) in.readObject();
            file.close();
            in.close();

        } catch (IOException  | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return recipeStorage;
    }
}

