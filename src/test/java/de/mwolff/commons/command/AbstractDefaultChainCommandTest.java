package de.mwolff.commons.command;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.samplecommands.ExceptionCommand;
import de.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class AbstractDefaultChainCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void executeAsChainReturnsFalse() throws Exception {
        final ExceptionCommand<GenericParameterObject> defaultCommand = new ExceptionCommand<GenericParameterObject>();
        Assert.assertThat(defaultCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT), Matchers.is(false));
    }

    @Test
    public void executeAsChainReturnsTrue() throws Exception {
        final SimpleTestCommand<GenericParameterObject> simpleTestCommand = new SimpleTestCommand<GenericParameterObject>();
        Assert.assertThat(simpleTestCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT), Matchers.is(true));
    }

    @Test
    public void executeTest() throws Exception {
        final ExceptionCommand<GenericParameterObject> exceptionCommand = new ExceptionCommand<GenericParameterObject>();
        final GenericParameterObject defaultContext = new DefaultParameterObject();
        thrown.expect(CommandException.class);
        thrown.expectMessage("Method is not implemented yet.");
        exceptionCommand.execute(defaultContext);
    }

}
