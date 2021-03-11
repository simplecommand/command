package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.MatcherAssert.assertThat;

public class DoneTestCommandTest {

    @Test
    public void testExecuteCommand() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final DoneTestCommand<GenericParameterObject> doneTestCommand = new DoneTestCommand<>();
        final CommandTransition result = doneTestCommand.executeCommand(context);
        assertThat(result, CoreMatchers.is(CommandTransition.DONE));
    }
}
