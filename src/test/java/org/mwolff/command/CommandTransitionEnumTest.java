package org.mwolff.command;

import org.junit.Test;

/**
 * This is a fake test for coverage the enum. Enums generates byte codes which
 * eclemma cannot deal with.
 * 
 * @author mwolff
 *
 */
public class CommandTransitionEnumTest implements CommandTransitionEnum {

    @Test
    public void testName() throws Exception {
        CommandTransition.valueOf(CommandTransition.SUCCESS.toString());
        CommandTransition.valueOf(CommandTransition.FAILURE.toString());
        CommandTransition.valueOf(CommandTransition.ABORT.toString());
    }
}
