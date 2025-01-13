package org.locations.dietplanner.Implementation.command;

import org.locations.dietplanner.Interfaces.ICommand;


public class ExportToJSONCommand<T> implements ICommand<Void> {
    private String filename;
    private T object;
    private MemoryService memoryService;

    public ExportToJSONCommand(String filename, T object){
        this.filename = filename;
        this.object = object;
        this.memoryService = MemoryService.getInstance();
    }
    @Override
    public Void execute() {
        memoryService.exportMealsToJSON(filename,object);
        return null;
    }
}
