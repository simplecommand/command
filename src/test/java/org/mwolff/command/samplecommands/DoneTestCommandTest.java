package org.mwolff.command.samplecommands;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class DoneTestCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExecute() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("DONE.");
        final GenericParameterObject context = new DefaultParameterObject();
        DoneTestCommand<GenericParameterObject> doneTestCommand = new DoneTestCommand<>();
        doneTestCommand.execute(context);
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        DoneTestCommand<GenericParameterObject> doneTestCommand = new DoneTestCommand<>();
        CommandTransition result = doneTestCommand.executeCommand(context);
        assertThat(result, CoreMatchers.is(CommandTransition.DONE));
    }
}
