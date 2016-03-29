package de.mwolff.commons.command.samplecommands;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.DefaultParameterObject;
import de.mwolff.commons.command.GenericParameterObject;
import de.mwolff.commons.command.iface.CommandException;

public class ExceptionCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExceptionThrowd() throws Exception {
        thrown.expect(CommandException.class);
        final GenericParameterObject context = new DefaultParameterObject();
        final ExceptionCommand<GenericParameterObject> exceptionCommand = new ExceptionCommand<GenericParameterObject>();
        exceptionCommand.execute(context);
        final String value = context.getAsString("executed");
        Assert.assertThat(value, Matchers.is("true"));
    }
}
