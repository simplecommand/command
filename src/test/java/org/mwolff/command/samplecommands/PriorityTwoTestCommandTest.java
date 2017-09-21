package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class PriorityTwoTestCommandTest {

    @Test
    public void testExecuteCommand() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityTwoTestCommand<>();
        command.executeCommand(context);
        Assert.assertThat(context.getAsString("PriorityTwoTestCommand"), CoreMatchers.is("PriorityTwoTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("2-"));
    }

    @Test
    public void testExecuteCommandAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityTwoTestCommand<>();
        final CommandTransition result = command.executeCommandAsChain(context);
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("2-"));
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.NEXT));
    }

}
