package org.locations.dietplanner.Implementation.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;

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
    public<T> void exportMealsToJSON(String fileName, T Object){
        try {
            objectMapper.writeValue (new File(fileName), Object);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public <T> T importMealsFromJSON(String filename,TypeReference<T> type){
        try{
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(new File(filename), type);
        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}

