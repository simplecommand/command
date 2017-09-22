package org.mwolff.command.testcommand;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import org.junit.Before;
import org.junit.Test;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class TestCommandTest {

    private GenericParameterObject defaultParameterObject;

    @Before
    public void setUp() {
        defaultParameterObject = DefaultParameterObject.getInstance();
    }
    
    @Test
    public void testWritePattern() throws Exception {
        // given
        TestCommand testCommand = new TestCommand("1-", null);
        
        // when
        testCommand.execute(defaultParameterObject);
        CommandTransition result = testCommand.executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-"));
        assertThat(result, is(SUCCESS));
    }

    private String getResult() {
        String strResult = defaultParameterObject.getAsString("resultString");
        return strResult;
    }        

       
    @Test
    public void testTwoInARow() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(new TestCommand("1-", null))
                                                          .addCommand(new TestCommand("2-", null))
                                                           .executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-2-"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testThreeInARow() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(new TestCommand("1-", null))
                                                          .addCommand(new TestCommand("2-", null))
                                                          .addCommand(new TestCommand("3-", null))
                                                           .executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-2-3-"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testThreeInARowPriority() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(3, new TestCommand("1-", null))
                                                          .addCommand(1, new TestCommand("2-", null))
                                                          .addCommand(2, new TestCommand("3-", null))
                                                          .executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("2-3-1-"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testThreeInARowAsChainToBreaks() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(new TestCommand("1-", CommandTransition.SUCCESS))
                                                          .addCommand(new TestCommand("2-", CommandTransition.DONE))
                                                          .addCommand(new TestCommand("3-", CommandTransition.SUCCESS))
                                                          .executeCommandAsChain(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-2-"));
        assertThat(result, is(DONE));
    }
}
