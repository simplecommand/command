package org.mwolff.command;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class AbstractDefaultCommandTest {

    class MyTestClass extends AbstractDefaultCommand<String> {

        @Override
        public CommandTransition executeCommand(String parameterObject) {
            return null;
        }

    }

    @Test
    public void testExecute() throws Exception {
        final MyTestClass instance = new MyTestClass();
        instance.executeCommand(null);
        Assert.assertThat(instance.executeCommand(null), CoreMatchers.nullValue());
    }

}
