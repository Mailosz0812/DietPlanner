package org.locations.dietplanner.Implementation.command;

import com.fasterxml.jackson.core.type.TypeReference;
import org.locations.dietplanner.Interfaces.ICommand;

public class MemoryCommandInvoker {
    private ICommand<?> command;
    public MemoryCommandInvoker(){
    }
    public <T> T executeCommand() {
        return (T) command.execute();
    }

    public void setCommand(ICommand<?> command) {
        this.command = command;
    }

}
