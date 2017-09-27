package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class ProcessTestCommandStartTest {

    @Test
    void executeAsProcessStart() {
        ProcessTestCommandStart<GenericParameterObject> processTestCommandStart = new ProcessTestCommandStart<>();
        String result = processTestCommandStart.executeAsProcess("start", DefaultParameterObject.NULLCONTEXT);
        assertThat(result, nullValue());
    }
    
}
