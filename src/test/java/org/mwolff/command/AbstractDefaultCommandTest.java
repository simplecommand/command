package org.mwolff.command;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AbstractDefaultCommandTest {

    class MyTestClass extends AbstractDefaultCommand<String> {

        @Override
        public CommandTransition executeCommand(String parameterObject) {
            return null;
        }

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExecute() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        final MyTestClass instance = new MyTestClass();
        instance.execute(null);
    }

}
