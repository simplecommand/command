/*         Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
 *
 *         This library is free software; you can redistribute it and/or
 *         modify it under the terms of the GNU Lesser General Public
 *         License as published by the Free Software Foundation; either
 *         version 2.1 of the License, or (at your option) any later version.
 *
 *         This library is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         Lesser General Public License for more details.
 *
 *         You should have received a copy of the GNU Lesser General Public
 *         License along with this library; if not, write to the Free Software
 *         Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *         02110-1301 USA
 */
package org.mwolff.command.sax;

import org.apache.log4j.Logger;
import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.Command;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.process.AbstractDefaultProcessCommand;
import org.mwolff.command.process.Transition;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.mwolff.command.CommandTransition.FAILURE;
import static org.mwolff.command.CommandTransition.SUCCESS;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

public class ActionListToCommandContainerCommand extends AbstractDefaultCommand<SaxParameterObject> {

    private static final Logger LOG = Logger.getLogger(ActionListToCommandContainerCommand.class);

    @SuppressWarnings({ "unchecked" })
    @Override
    public CommandTransition executeCommand(SaxParameterObject parameterObject) {

        final DefaultCommandContainer<Object> defaultCommandContainer = new DefaultCommandContainer<>();

        final List<Action> actionList = (List<Action>) parameterObject.get(ACTION_LIST.toString());

        for (final Action action : actionList) {

            final String classname = action.getClassname();
            Command<Object> command;
            try {
                command = (Command<Object>) Class.forName(classname).getConstructor().newInstance();
                defaultCommandContainer.addCommand(command);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                LOG.error(e);
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
