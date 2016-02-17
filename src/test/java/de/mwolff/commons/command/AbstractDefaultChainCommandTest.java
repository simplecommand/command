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
    public void executeTest() throws Exception {
        final ExceptionCommand<GenericContext> exceptionCommand = new ExceptionCommand<GenericContext>();
        final GenericContext defaultContext = new DefaultContext();
        thrown.expect(CommandException.class);
        thrown.expectMessage("Method is not implemented yet.");
        exceptionCommand.execute(defaultContext);
    }

    @Test
    public void executeAsChainReturnsFalse() throws Exception {
        final ExceptionCommand<GenericContext> defaultCommand = new ExceptionCommand<GenericContext>();
        Assert.assertThat(defaultCommand.executeAsChain(DefaultContext.NULLCONTEXT), Matchers.is(false));
    }

    @Test
    public void executeAsChainReturnsTrue() throws Exception {
        final SimpleTestCommand<GenericContext> simpleTestCommand = new SimpleTestCommand<GenericContext>();
        Assert.assertThat(simpleTestCommand.executeAsChain(DefaultContext.NULLCONTEXT), Matchers.is(true));
    }

}
