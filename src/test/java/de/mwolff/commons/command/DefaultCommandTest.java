package de.mwolff.commons.command;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.Context;
import de.mwolff.commons.command.samplecommands.ExceptionCommand;
import de.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class DefaultCommandTest {
    
    @Rule
    public ExpectedException thrown= ExpectedException.none();


    @Test
    public void getProcessIDTest() throws Exception {
        DefaultCommand<Context> defaultCommand = new DefaultCommand<Context>("myProcessID");
        String id = defaultCommand.getProcessID();
        assertThat(id, is("myProcessID"));
    }
    
    @Test
    public void executeTest() throws Exception {
        ExceptionCommand<GenericContext> exceptionCommand = new ExceptionCommand<GenericContext>();
        GenericContext defaultContext = new DefaultContext();
        thrown.expect(CommandException.class);
        thrown.expectMessage("Method is not implemented yet.");
        exceptionCommand.execute(defaultContext);
    }
    
    @Test
    public void executeAsChainReturnsFalse() throws Exception {
        ExceptionCommand<GenericContext> defaultCommand = new ExceptionCommand<GenericContext>();
        assertThat(defaultCommand.executeAsChain(DefaultContext.NULLCONTEXT), is(false));
    }
    
    @Test
    public void executeAsChainReturnsTrue() throws Exception {
        SimpleTestCommand<GenericContext> simpleTestCommand = new SimpleTestCommand<GenericContext>();
        assertThat(simpleTestCommand.executeAsChain(DefaultContext.NULLCONTEXT), is(true));
    }
    
    
}
