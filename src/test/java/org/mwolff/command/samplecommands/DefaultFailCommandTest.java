package org.mwolff.command.samplecommands;

import org.junit.jupiter.api.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultFailCommandTest {

    @Test
    void executeAsProcessStart() {
        DefaultFailCommand defaultFailCommand = new DefaultFailCommand();
        String result = defaultFailCommand.executeAsProcess("", new DefaultParameterObject());
        assertThat(result, is("FAIL"));
    }

    @Test
    void executeAsProcess() {
        DefaultFailCommand defaultFailCommand = new DefaultFailCommand();
        String result = defaultFailCommand.executeAsProcess(new DefaultParameterObject());
        assertThat(result, is("FAIL"));
    }
    
}
