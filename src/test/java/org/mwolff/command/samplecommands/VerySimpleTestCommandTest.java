package org.mwolff.command.samplecommands;

import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
