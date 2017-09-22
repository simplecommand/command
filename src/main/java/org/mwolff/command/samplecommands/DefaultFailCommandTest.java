package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;

public class DefaultFailCommandTest {

    @Test
    void executeAsProcessStart() {
        DefaultFailCommand defaultFailCommand = new DefaultFailCommand();
        String result = defaultFailCommand.executeAsProcess("", DefaultParameterObject.NULLCONTEXT);
        assertThat(result, is("FAIL"));
    }

    @Test
    void executeAsProcess() {
        DefaultFailCommand defaultFailCommand = new DefaultFailCommand();
        String result = defaultFailCommand.executeAsProcess(DefaultParameterObject.NULLCONTEXT);
        assertThat(result, is("FAIL"));
    }
    
}
