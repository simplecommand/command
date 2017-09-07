package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.Command;
import org.mwolff.command.CommandException;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class SimpleTestCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("deprecation")
    @Test
    public void testExecute() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        command.execute(context);
        Assert.assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testExecuteNull() throws Exception {
        thrown.expect(CommandException.class);
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        command.execute(null);
    }

}
