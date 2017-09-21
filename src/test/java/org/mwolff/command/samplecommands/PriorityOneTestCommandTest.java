package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class PriorityOneTestCommandTest {

    @Test
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityOneTestCommand<>();
        final CommandTransition result = command.executeCommandAsChain(context);
        Assert.assertThat(context.getAsString("PriorityOneTestCommand"), CoreMatchers.is("PriorityOneTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("1-"));
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.NEXT));
    }
}
