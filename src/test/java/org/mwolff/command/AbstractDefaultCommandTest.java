package org.mwolff.command;

import static org.mwolff.command.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class AbstractDefaultCommandTest {

    class MyTestClass extends AbstractDefaultCommand<String> {
    }

    @Test
    public void testExecuteCommandWithNullContext() throws Exception {
        final MyTestClass instance = new MyTestClass();
        final CommandTransition result = instance.executeCommand(null);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
    }

    @Test
    public void testExecuteCommandWithValidContext() throws Exception {
        final MyTestClass instance = new MyTestClass();
        final CommandTransition result = instance.executeCommand("valid");
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
        instance.executeCommand(null);
        Assert.assertThat(instance.executeCommand(null), CoreMatchers.is(FAILURE));
    }

}
