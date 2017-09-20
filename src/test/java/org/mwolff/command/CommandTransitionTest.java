package org.mwolff.command;

import org.junit.Test;

public class CommandTransitionTest {
    
    @Test
    public void testName() throws Exception {
        CommandTransition.valueOf(CommandTransition.SUCCESS.toString());
        CommandTransition.valueOf(CommandTransition.FAILURE.toString());
        CommandTransition.valueOf(CommandTransition.DONE.toString());
    }
    
}
