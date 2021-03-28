/** Simple Command Framework.
 *
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Download:
 *         https://github.com/simplecommand/command.git
 *
 *         Copyright (C) 2018-2021 Manfred Wolff and the simple command community
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
 *         02110-1301
 *         USA */

package org.mwolff.command.sax;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.interfaces.*;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mwolff.command.interfaces.CommandTransition.FAILURE;
import static org.mwolff.command.interfaces.CommandTransition.SUCCESS;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

public class ActionListToCommandContainerCommandTest {

    private final List<Action> actionList = new ArrayList<>();
    private Transition transition;

    @BeforeEach
    public void setUp() {

        final Action action = new Action();
        action.setClassname("org.mwolff.command.samplecommands.ProcessTestCommandNext");
        action.setId("action");

        transition = new Transition() {

            @Override
            public void setTarget(final String target) {
            }

            @Override
            public void setReturnValue(final String returnValue) {
            }

            @Override
            public String getTarget() {
                return "target";
            }

            @Override
            public String getReturnValue() {
                return "START";
            }
        };
        action.setTransition(transition);
        actionList.add(action);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public void testActionCreationOK() throws Exception {

        final ActionListToCommandContainerCommand actionListToCommandContainerCommand = new ActionListToCommandContainerCommand();
        final SaxParameterObject context = new SaxParameterObject();
        context.put(ACTION_LIST, actionList);
        final CommandTransition result = actionListToCommandContainerCommand.executeCommand(context);
        final CommandContainer<GenericParameterObject> container = (CommandContainer<GenericParameterObject>) context
                .get(COMMAND_CONTAINER);
        assertThat(container, CoreMatchers.notNullValue());
        assertThat(result, CoreMatchers.is(SUCCESS));
        final Map<Integer, Command<GenericParameterObject>> commandList = (Map<Integer, Command<GenericParameterObject>>) ReflectionTestUtils
                .getField(container, "commandList");
        assertThat(commandList, CoreMatchers.notNullValue());
        assertThat(commandList.size(), CoreMatchers.is(1));
        final ProcessCommand<GenericParameterObject> command = (ProcessCommand<GenericParameterObject>) commandList
                .values().iterator().next();
        assertThat(command, CoreMatchers.notNullValue());
        assertThat(command, CoreMatchers.instanceOf(AbstractDefaultCommand.class));
        final List<Transition> transitions = command.getTransitionList();
        assertThat(transitions, CoreMatchers.notNullValue());
        assertThat(transitions.size(), CoreMatchers.is(1));
    }

    @Test
    public void testInvalidClassname() throws Exception {
        final Action action = new Action();
        action.setClassname("false.package.name.Class");
        action.setId("action");
        actionList.add(action);
        final ActionListToCommandContainerCommand actionListToCommandContainerCommand = new ActionListToCommandContainerCommand();
        final SaxParameterObject context = new SaxParameterObject();
        context.put(ACTION_LIST, actionList);
        final CommandTransition result = actionListToCommandContainerCommand.executeCommand(context);
        assertThat(result, CoreMatchers.is(FAILURE));
        final String error = context.getAsString(ERROR_STRING);
        assertThat(error, CoreMatchers.is("Error while instaciating class via reflection"));
    }
    
    @Test
    void testCoverage() {
        transition.setTarget("");
        transition.setReturnValue("");
        assertThat(transition.getTarget(), CoreMatchers.is("target"));
        assertThat(transition.getReturnValue(), CoreMatchers.is("START"));
    }
}
