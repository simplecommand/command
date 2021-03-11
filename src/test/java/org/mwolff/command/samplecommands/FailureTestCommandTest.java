package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.MatcherAssert.assertThat;

public class FailureTestCommandTest {

    @Test
    public void testExecuteAsChain() throws Exception {
        final DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        final FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        final CommandTransition result = failureTestCommand.executeCommandAsChain(defaultParameterObject);
        assertThat(result, CoreMatchers.is(CommandTransition.FAILURE));
    }
}
