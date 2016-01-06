package de.mwolff.commons.command.samplecommands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.DefaultContext;
import de.mwolff.commons.command.GenericContext;
import de.mwolff.commons.command.iface.CommandException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExceptionCommandTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    
    @Test
    public void testExceptionThrowd() throws Exception {
        thrown.expect(CommandException.class);
        GenericContext context = new DefaultContext();
        ExceptionCommand<GenericContext> exceptionCommand = new ExceptionCommand<GenericContext>();
        exceptionCommand.execute(context);
        String value = context.getAsString("executed");
        assertThat(value, is("true"));
    }
}
