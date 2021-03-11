package org.mwolff.command.chain;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mwolff.command.CommandTransition.NEXT;
import static org.mwolff.command.CommandTransition.SUCCESS;

public class ChainCommandTest {

    @Test
    public void testInterfaceDefaultExecuteCommandAsChain() {

        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new AbstractDefaultChainCommand<GenericParameterObject>() {

            @Override
            public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
                return SUCCESS;
            }

        };

        CommandTransition transition = command.executeCommand(context);
        assertThat(transition, CoreMatchers.is(SUCCESS));
        transition = command.executeCommandAsChain(context);
        assertThat(transition, CoreMatchers.is(NEXT));
    }

    @Test
    public void testInterfaceDefaultExecuteCommandAsChainFAILED() {

        final ChainCommand<GenericParameterObject> command = new AbstractDefaultChainCommand<GenericParameterObject>() {

            @Override
            public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
                return SUCCESS;
            }

        };

        CommandTransition transition = command.executeCommand(null);
        assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
        transition = command.executeCommandAsChain(null);
        assertThat(transition, CoreMatchers.is(CommandTransition.NEXT));
    }

}
