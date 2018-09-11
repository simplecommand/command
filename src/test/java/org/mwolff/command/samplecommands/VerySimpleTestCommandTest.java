package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class VerySimpleTestCommandTest {

    @Test
    void executeCommand() {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        VerySimpleTestCommand<GenericParameterObject> verySimpleTestCommand = new VerySimpleTestCommand<>();
        CommandTransition result = verySimpleTestCommand.executeCommand(context);
        String resultString = context.getAsString("priority");
        assertThat(result, is(CommandTransition.SUCCESS));
        assertThat(resultString, is("S-"));
    }
}
