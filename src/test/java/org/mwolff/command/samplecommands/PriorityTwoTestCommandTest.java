package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
        assertThat(context.getAsString("PriorityTwoTestCommand"), is("PriorityTwoTestCommand"));
        assertThat(context.getAsString("priority"), is("2-"));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityTwoTestCommand<>();
        boolean result = command.executeAsChain(context);
        assertThat(context.getAsString("priority"), is("B-"));
        assertThat(result, is(Boolean.TRUE));
    }

}
