package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class PriorityThreeTestCommandTest {

    @Test
    public void testExecuteCommand() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityThreeTestCommand<>();
        command.executeCommand(context);
        Assert.assertThat(context.getAsString("PriorityThreeTestCommand"), CoreMatchers.is("PriorityThreeTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("3-"));
    }

    @Test
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityThreeTestCommand<>();
        context.put("priority", "");
        final CommandTransition result = command.executeCommandAsChain(context);
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("3-"));
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.NEXT));
    }

}
