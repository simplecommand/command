package org.mwolff.command.sax;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import org.junit.Test;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;

public class InputSourceReaderCommandTest {

    @Test
    public void testInvalidFilenName() throws Exception {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<>();
        context.put(InputSourceReaderCommand.file_name, "invalidFile.xml");
        CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        assertThat(result, is(FAILURE));
        String error = context.getAsString(InputSourceReaderCommand.error_string);
        assertThat(error, is("Error reading resource. Resource not found."));
    }

    @Test
    public void testValidFilenName() throws Exception {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<>();
        context.put(InputSourceReaderCommand.file_name, "commandChainProcess.xml");
        CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        assertThat(result, is(SUCCESS));
        InputSource source = (InputSource) context.get(InputSourceReaderCommand.input_source);
        assertThat(source, notNullValue());
    }

}
