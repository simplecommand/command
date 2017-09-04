package org.mwolff.command.samplecommands;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class FailureTestCommandTest {

    @Test
    public void testExecute() throws Exception {
        DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        failureTestCommand.execute(defaultParameterObject);
        assertThat(defaultParameterObject.getAsString("status"), CoreMatchers.is("proceeded"));
    }

    @Test
    public void testExecuteAsChain() throws Exception {
        DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        boolean result = failureTestCommand.executeAsChain(defaultParameterObject);
        assertThat(defaultParameterObject.getAsString("status"), CoreMatchers.is("proceeded"));
        assertThat(result, CoreMatchers.is(Boolean.FALSE));
    }
}
