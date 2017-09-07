package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class PriorityTwoTestCommandTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testExecute() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityTwoTestCommand<>();
        command.execute(context);
        Assert.assertThat(context.getAsString("PriorityTwoTestCommand"), CoreMatchers.is("PriorityTwoTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("2-"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityTwoTestCommand<>();
        final boolean result = command.executeAsChain(context);
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("B-"));
        Assert.assertThat(result, CoreMatchers.is(Boolean.TRUE));
    }

}
