package org.mwolff.command;

import static org.mwolff.command.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AbstractDefaultCommandTest {

    final class Testclass extends AbstractDefaultCommand<String> {
    }

    @Test
    void testDefaultExecuteCommandSuccess() {
        final Testclass testclass = new Testclass();
        final CommandTransition result = testclass.executeCommand("notnull");
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
    }

    @Test
    void testDefaultExecuteCommandFailed() {
        final Testclass testclass = new Testclass();
        final CommandTransition result = testclass.executeCommand(null);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
    }

}
