package org.mwolff.command.chain;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class ChainCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInterfaceDefaultExecuteCommandAsChain() {

        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new ChainCommand<GenericParameterObject>() {

            @Override
            public void execute(GenericParameterObject parameterObject) throws CommandException {
                throw new UnsupportedOperationException("Use executeCommand() instead");
            }

            @Override
            public boolean executeAsChain(GenericParameterObject parameterObject) {
                return false;
            }

            @Override
            public CommandTransition executeCommandAsChain(GenericParameterObject parameterObject) {
                return ChainCommand.super.executeCommandAsChain(parameterObject);
            }

        };

        CommandTransition transition = command.executeCommand(context);
        Assert.assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
        transition = command.executeCommandAsChain(context);
        Assert.assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
    }

    @Test
    public void testInterfaceDefaultExecuteCommandAsChainFAILED() {

        final ChainCommand<GenericParameterObject> command = new ChainCommand<GenericParameterObject>() {

            @Override
            public void execute(GenericParameterObject parameterObject) throws CommandException {
                throw new UnsupportedOperationException("Use executeCommand() instead");
            }

            @Override
            public boolean executeAsChain(GenericParameterObject parameterObject) {
                return false;
            }

            @Override
            public CommandTransition executeCommandAsChain(GenericParameterObject parameterObject) {
                return ChainCommand.super.executeCommandAsChain(parameterObject);
            }

        };

        CommandTransition transition = command.executeCommand(null);
        Assert.assertThat(transition, CoreMatchers.is(CommandTransition.FAILURE));
        transition = command.executeCommandAsChain(null);
        Assert.assertThat(transition, CoreMatchers.is(CommandTransition.FAILURE));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInterfaceDefaultExecute() throws Exception {

        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new ChainCommand<GenericParameterObject>() {

            @Override
            public void execute(GenericParameterObject parameterObject) throws CommandException {
                throw new UnsupportedOperationException("Use executeCommand() instead");
            }

            @Override
            public boolean executeAsChain(GenericParameterObject parameterObject) {
                throw new UnsupportedOperationException("Use executeCommandAsChain() instead");
            }

            @Override
            public CommandTransition executeCommandAsChain(GenericParameterObject parameterObject) {
                return ChainCommand.super.executeCommandAsChain(parameterObject);
            }

        };

        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Use executeCommandAsChain() instead");
        command.executeAsChain(context);
    }

}
