package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class FailureTestCommandTest {

    @Test
    public void testExecute() throws Exception {
        final DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        final FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        failureTestCommand.execute(defaultParameterObject);
        Assert.assertThat(defaultParameterObject.getAsString("status"), CoreMatchers.is("proceeded"));
    }

    @Test
    public void testExecuteAsChain() throws Exception {
        final DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        final FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        final boolean result = failureTestCommand.executeAsChain(defaultParameterObject);
        Assert.assertThat(defaultParameterObject.getAsString("status"), CoreMatchers.is("proceeded"));
        Assert.assertThat(result, CoreMatchers.is(Boolean.FALSE));
    }
}
