package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class FailureTestCommandTest {

    @Test
    public void testExecuteAsChain() throws Exception {
        final DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        final FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        final CommandTransition result = failureTestCommand.executeCommandAsChain(defaultParameterObject);
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.FAILURE));
    }
}
