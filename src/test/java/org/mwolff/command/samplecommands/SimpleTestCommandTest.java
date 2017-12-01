package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransition.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.Command;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class SimpleTestCommandTest {

    @Test
    public void testExecuteCommand() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        CommandTransition result = command.executeCommand(context);
        assertThat(context.getAsString("SimpleTestCommand"), is("SimpleTestCommand"));
        assertThat(context.getAsString("resultString"), is("S-"));
        assertThat(result, is(SUCCESS));
    }
    
    @Test
    public void testFailure() throws Exception {
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        CommandTransition result = command.executeCommand(null);
        assertThat(result, is(FAILURE));
    }
}
