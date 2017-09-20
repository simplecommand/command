package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;

public class InputSourceReaderCommandTest {

    @Test
    public void testInvalidFilenName() throws Exception {
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        final InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<>();
        context.put(FILE_NAME.toString(), "invalidFile.xml");
        final CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
        final String error = context.getAsString(ERROR_STRING.toString());
        Assert.assertThat(error, CoreMatchers.is("Error reading resource. Resource not found."));
    }

    @Test
    public void testValidFilenName() throws Exception {
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        final InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<>();
        context.put(FILE_NAME.toString(), "commandChainProcess.xml");
        final CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
        final InputSource source = (InputSource) context.get(INPUT_SOURCE.toString());
        Assert.assertThat(source, CoreMatchers.notNullValue());
    }

}
