package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import java.util.List;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.Command;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.AbstractDefaultProcessCommand;
import org.mwolff.command.process.Transition;

public class ActionListToCommandContainerCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    @SuppressWarnings({ "unchecked" })
    @Override
    public CommandTransition executeCommand(T parameterObject) {

        final DefaultCommandContainer<T> defaultCommandContainer = new DefaultCommandContainer<>();

        final List<Action> actionList = (List<Action>) parameterObject.get(GlobalCommandConstants.action_list);

        for (final Action action : actionList) {

            final String classname = action.getClassname();
            Command<Object> command;
            try {
                command = (Command<Object>) Class.forName(classname).newInstance();
                defaultCommandContainer.addCommand((Command<T>) command);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                parameterObject.put(GlobalCommandConstants.error_string,
                        "Error while instaciating class via reflection");
                return FAILURE;
            }

            if (command instanceof AbstractDefaultProcessCommand<?>) {
                ((AbstractDefaultProcessCommand<T>) command).setProcessID(action.getId());
                final List<Transition> transitions = action.getTransitions();
                for (final Transition transition : transitions) {
                    ((AbstractDefaultProcessCommand<T>) command).addTransition(transition);
                }
            }

        }
        parameterObject.put(GlobalCommandConstants.command_container, defaultCommandContainer);
        return SUCCESS;
    }

}
