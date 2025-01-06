package org.locations.dietplanner.Implementation.command;

import com.fasterxml.jackson.core.type.TypeReference;
import org.locations.dietplanner.Interfaces.ICommand;

public class ImportToJSONCommand<T> implements ICommand<T> {
    private MemoryService memoryService;
    private String fileName;
    private TypeReference<T> type;

    public ImportToJSONCommand(String fileName, TypeReference<T> type){
        this.fileName = fileName;
        this.memoryService = MemoryService.getInstance();
        this.type = type;
    }
    @Override
    public T execute() {
        return memoryService.importMealsFromJSON(fileName,type);
    }
}
