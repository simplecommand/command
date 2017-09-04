package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class PriorityThreeTestCommandTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testExecute() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityThreeTestCommand<>();
        command.execute(context);
        assertThat(context.getAsString("PriorityThreeTestCommand"), is("PriorityThreeTestCommand"));
        assertThat(context.getAsString("priority"), is("3-"));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new PriorityThreeTestCommand<>();
        context.put("priority", "");
        boolean result = command.executeAsChain(context);
        assertThat(context.getAsString("priority"), is("C-"));
        assertThat(result, is(Boolean.FALSE));
    }

}
