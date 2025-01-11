package org.locations.dietplanner.Implementation.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeStorage;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.*;
import java.util.List;

public class MemoryService {

    private static MemoryService service;
    private ObjectMapper objectMapper = new ObjectMapper();

    private MemoryService(){}
    public static MemoryService getInstance(){
        if(service == null){
            service = new MemoryService();
        }
        return service;
    }
    public<T> void exportRecipeToJSON(String fileName, List<T> list){
        try {
            objectMapper.writeValue(new File(fileName), list);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public <T> T importMealsFromJSON(String filename,TypeReference<T> type){
        try{
            T mealsList = objectMapper.readValue(new File(filename), type);
            objectMapper.registerModule(new JavaTimeModule());
            return mealsList;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}

