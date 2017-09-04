package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class PriorityOneTestCommandTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testExecute() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityOneTestCommand<>();
        command.execute(context);
        assertThat(context.getAsString("PriorityOneTestCommand"), is("PriorityOneTestCommand"));
        assertThat(context.getAsString("priority"), is("1-"));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityOneTestCommand<>();
        boolean result = command.executeAsChain(context);
        assertThat(context.getAsString("PriorityOneTestCommand"), is("PriorityOneTestCommand"));
        assertThat(context.getAsString("priority"), is("1-"));
        assertThat(result, is(Boolean.FALSE));
    }
}
