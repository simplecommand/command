package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.util.List;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.Command;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.AbstractDefaultProcessCommand;
import org.mwolff.command.process.Transition;

public class ActionListToCommandContainerCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public CommandTransition executeCommand(T parameterObject)  {
        
        DefaultCommandContainer<T> defaultCommandContainer = new DefaultCommandContainer<T>();
        
        List<Action> actionList = (List<Action>) parameterObject.get(action_list);
        
        for (Action action : actionList) {
        
            String classname = action.getClassname();
            Command<Object> command;
            try {
                command = (Command<Object>) Class.forName(classname).newInstance();
                defaultCommandContainer.addCommand((Command<T>) command);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                parameterObject.put(error_string, "Error while instaciating class via reflection");
                return FAILURE;
            }
            
            if (command instanceof AbstractDefaultProcessCommand<?>) {
                List<Transition> transitions = action.getTransitions();
                for (Transition transition : transitions) {
                    ((AbstractDefaultProcessCommand<T>) command).addTransition(transition);
                }
            }
            
        }
        parameterObject.put(command_container, defaultCommandContainer);
        return SUCCESS;
    }
    
}
