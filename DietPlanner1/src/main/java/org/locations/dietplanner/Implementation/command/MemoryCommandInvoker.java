package org.locations.dietplanner.Implementation.command;

import com.fasterxml.jackson.core.type.TypeReference;
import org.locations.dietplanner.Interfaces.ICommand;

public class MemoryCommandInvoker {
    private ICommand<?> command;
    private TypeReference<?> typeReference;
    public MemoryCommandInvoker(ICommand command,TypeReference typeReference){
        this.command = command;
        this.typeReference = typeReference;
    }
    public <T> T executeCommand(ICommand<T> command) {
        return command.execute();
    }
}
