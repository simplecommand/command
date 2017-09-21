package org.mwolff.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransition.*;

import org.junit.jupiter.api.Test;


public class AbstractDefaultCommandTest {
    
    final class Testclass extends AbstractDefaultCommand<String> {
    }

    @Test
    void testDefaultExecuteCommandSuccess() {
        Testclass testclass = new Testclass();
        CommandTransition result = testclass.executeCommand("notnull");
        assertThat(result, is(SUCCESS));
    }
    
    @Test
    void testDefaultExecuteCommandFailed() {
        Testclass testclass = new Testclass();
        CommandTransition result = testclass.executeCommand(null);
        assertThat(result, is(FAILURE));
    }
    
}
