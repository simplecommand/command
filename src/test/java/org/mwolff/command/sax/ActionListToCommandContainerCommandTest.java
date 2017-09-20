package org.mwolff.command.sax;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.process.Transition;
import org.springframework.test.util.ReflectionTestUtils;

public class ActionListToCommandContainerCommandTest {

    private final List<Action> actionList = new ArrayList<>();

    @Before
    public void setUp() {

        final Action action = new Action();
        action.setClassname("org.mwolff.command.samplecommands.ProcessTestCommandNext");
        action.setId("action");

        final Transition transition = new Transition() {

            @Override
            public void setTarget(String target) {
            }

            @Override
            public void setReturnValue(String returnValue) {
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

        final ActionListToCommandContainerCommand<GenericParameterObject> actionListToCommandContainerCommand = new ActionListToCommandContainerCommand<>();
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        context.put(action_list.toString(), actionList);
        final CommandTransition result = actionListToCommandContainerCommand.executeCommand(context);
        final CommandContainer<GenericParameterObject> container = (CommandContainer<GenericParameterObject>) context
                .get(command_container.toString());
        Assert.assertThat(container, CoreMatchers.notNullValue());
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
        final Map<Integer, Command<GenericParameterObject>> commandList = (Map<Integer, Command<GenericParameterObject>>) ReflectionTestUtils
                .getField(container, "commandList");
        Assert.assertThat(commandList, CoreMatchers.notNullValue());
        Assert.assertThat(commandList.size(), CoreMatchers.is(1));
        final ProcessCommand<GenericParameterObject> command = (ProcessCommand<GenericParameterObject>) commandList
                .values().iterator().next();
        Assert.assertThat(command, CoreMatchers.notNullValue());
        Assert.assertThat(command, CoreMatchers.instanceOf(AbstractDefaultCommand.class));
        final List<Transition> transitions = command.getTransitionList();
        Assert.assertThat(transitions, CoreMatchers.notNullValue());
        Assert.assertThat(transitions.size(), CoreMatchers.is(1));
    }

    @Test
    public void testInvalidClassname() throws Exception {
        Action action = new Action();
        action.setClassname("false.package.name.Class");
        action.setId("action");
        actionList.add(action);
        ActionListToCommandContainerCommand<GenericParameterObject> actionListToCommandContainerCommand = new ActionListToCommandContainerCommand<>();
        GenericParameterObject context = DefaultParameterObject.getInstance();
        context.put(action_list.toString(), actionList);
        CommandTransition result = actionListToCommandContainerCommand.executeCommand(context);
        assertThat(result, is(FAILURE));
        String error = context.getAsString(error_string.toString());
        assertThat(error, is("Error while instaciating class via reflection"));
    }
}
