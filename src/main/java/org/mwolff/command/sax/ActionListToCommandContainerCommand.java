package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.Command;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.AbstractDefaultProcessCommand;
import org.mwolff.command.process.Transition;

public class ActionListToCommandContainerCommand extends AbstractDefaultCommand<SaxParameterObject> {

    private static final Logger         LOG   = Logger.getLogger(ActionListToCommandContainerCommand.class);

    
    @SuppressWarnings({ "unchecked" })
    @Override
    public CommandTransition executeCommand(SaxParameterObject parameterObject) {

        final DefaultCommandContainer<Object> defaultCommandContainer = new DefaultCommandContainer<>();

        final List<Action> actionList = (List<Action>) parameterObject.get(ACTION_LIST.toString());

        for (final Action action : actionList) {

            final String classname = action.getClassname();
            Command<Object> command;
            try {
                command = (Command<Object>) Class.forName(classname).newInstance();
                defaultCommandContainer.addCommand((Command<Object>) command);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOG.error(e);
                parameterObject.put(ERROR_STRING.toString(),
                        "Error while instaciating class via reflection");
                return FAILURE;
            }

            if (command instanceof AbstractDefaultProcessCommand<?>) {
                ((AbstractDefaultProcessCommand<Object>) command).setProcessID(action.getId());
                final List<Transition> transitions = action.getTransitions();
                for (final Transition transition : transitions) {
                    ((AbstractDefaultProcessCommand<Object>) command).addTransition(transition);
                }
            }

        }
        parameterObject.put(COMMAND_CONTAINER.toString(), defaultCommandContainer);
        return SUCCESS;
    }

}
