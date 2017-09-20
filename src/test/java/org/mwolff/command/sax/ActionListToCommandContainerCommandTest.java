package org.mwolff.command.sax;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.Transition;
import org.springframework.test.util.ReflectionTestUtils;

public class ActionListToCommandContainerCommandTest {

    private List<Action> actionList= new ArrayList<>();
    
    @Before
    public void setUp() {

        Action action = new Action();
        action.setClassname("org.mwolff.command.sax.SaxParserCommand");
        action.setId("action");

        Transition transition = new Transition() {

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

    @SuppressWarnings("unchecked")
    @Test
    public void testActionCreationOK() throws Exception {

        ActionListToCommandContainerCommand<GenericParameterObject> actionListToCommandContainerCommand = new ActionListToCommandContainerCommand<>();
        GenericParameterObject context = DefaultParameterObject.getInstance();
        context.put(action_list, actionList);
        CommandTransition result = actionListToCommandContainerCommand.executeCommand(context);
        CommandContainer<GenericParameterObject> container = (CommandContainer<GenericParameterObject>) context.get(command_container);
        assertThat(container, notNullValue());
        assertThat(result, is(SUCCESS));
        Map<Integer, Command<GenericParameterObject>> commandList = (Map<Integer, Command<GenericParameterObject>>) ReflectionTestUtils.getField(container, "commandList");
        assertThat(commandList, notNullValue());
        assertThat(commandList.size(), is(1));
    }
}
