package org.mwolff.command.samplecommands;

import org.junit.jupiter.api.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProcessTestCommandStartTest {

    @Test
    void executeAsProcessStart() {
        ProcessTestCommandStart<GenericParameterObject> processTestCommandStart = new ProcessTestCommandStart<>();
        String result = processTestCommandStart.executeAsProcess("start", new DefaultParameterObject());
        assertThat(result, nullValue());
    }
    
}
