package de.mwolff.commons.command.samplecommands;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.DefaultContext;
import de.mwolff.commons.command.GenericContext;
import de.mwolff.commons.command.iface.CommandException;

public class ExceptionCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExceptionThrowd() throws Exception {
        thrown.expect(CommandException.class);
        final GenericContext context = new DefaultContext();
        final ExceptionCommand<GenericContext> exceptionCommand = new ExceptionCommand<GenericContext>();
        exceptionCommand.execute(context);
        final String value = context.getAsString("executed");
        Assert.assertThat(value, Matchers.is("true"));
    }
}
