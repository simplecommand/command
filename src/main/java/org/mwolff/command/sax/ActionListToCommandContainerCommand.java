package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.util.List;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class ActionListToCommandContainerCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    @Override
    public CommandTransition executeCommand(T parameterObject) {
        DefaultCommandContainer<T> defaultCommandContainer = new DefaultCommandContainer<T>();
        
        List<Action> actionList = (List<Action>) parameterObject.get(action_list);
        
        for (Action action : actionList) {
            String classname = action.getClassname();
        }
        
        
        parameterObject.put(command_container, defaultCommandContainer);
        return SUCCESS;
    }
    
}
