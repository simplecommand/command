package de.mwolff.commons.command;

import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;

public class DefaultEndCommand <T extends ParameterObject> extends AbstractDefaultProcessCommand<T>{
   
    @Override
    public void execute(T context) throws CommandException {
    }
    
    @Override
    public String executeAsProcess(String startCommand, T context) {
        
        try {
            execute(context);
        } catch (CommandException e) {
        }
        return "END";
    }

}
