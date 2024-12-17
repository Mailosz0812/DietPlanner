package org.locations.dietplanner.Implementation.command;

import com.fasterxml.jackson.core.type.TypeReference;
import org.locations.dietplanner.Interfaces.ICommand;

import java.util.List;

public class ExportFromJSONCommand<T> implements ICommand<Void> {
    private String filename;
    private List<T> list;
    private MemoryService memoryService;

    public ExportFromJSONCommand(String filename,List<T> list,MemoryService memoryService){
        this.filename = filename;
        this.list = list;
        this.memoryService = memoryService;
    }
    @Override
    public Void execute() {
        memoryService.exportRecipeToJSON(filename,list);
        return null;
    }
}
