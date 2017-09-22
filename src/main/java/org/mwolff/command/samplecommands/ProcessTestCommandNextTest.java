package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class ProcessTestCommandNextTest {

    @Test
    void executeAsProcessNull( ) {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        String result = command.executeAsProcess(context);
        assertThat(result, is("OK"));
    }

    @Test
    void executeAsProcessNotNull( ) {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        context.put("counter", Integer.valueOf(1));
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        String result = command.executeAsProcess(context);
        assertThat(result, is(""));
    }
    
    @Test
    void executeCommand( ) {
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        CommandTransition result = command.executeCommand(null);
        assertThat(result, is(CommandTransition.SUCCESS));
        
    }
    
    @Test
    void getProceddID() {
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        String result = command.getProcessID();
        assertThat(result, nullValue());
    }
}
