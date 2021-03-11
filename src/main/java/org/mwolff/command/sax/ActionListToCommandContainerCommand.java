package org.mwolff.command.sax;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.Command;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.process.AbstractDefaultProcessCommand;
import org.mwolff.command.process.Transition;

import java.util.List;

import static org.mwolff.command.CommandTransition.FAILURE;
import static org.mwolff.command.CommandTransition.SUCCESS;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

public class ActionListToCommandContainerCommand extends AbstractDefaultCommand<SaxParameterObject> {


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
                defaultCommandContainer.addCommand(command);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                parameterObject.put(ERROR_STRING.toString(), "Error while instaciating class via reflection");
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
