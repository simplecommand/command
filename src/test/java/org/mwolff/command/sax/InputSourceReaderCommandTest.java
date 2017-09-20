package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.xml.sax.InputSource;

public class InputSourceReaderCommandTest {

    @Test
    public void testInvalidFilenName() throws Exception {
        final SaxParameterObject context = new SaxParameterObject();
        final InputSourceReaderCommand inputSourceReaderCommand = new InputSourceReaderCommand();
        context.put(FILE_NAME, "invalidFile.xml");
        final CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
        final String error = context.getAsString(ERROR_STRING);
        Assert.assertThat(error, CoreMatchers.is("Error reading resource. Resource not found."));
    }

    @Test
    public void testValidFilenName() throws Exception {
        final SaxParameterObject context = new SaxParameterObject();
        final InputSourceReaderCommand inputSourceReaderCommand = new InputSourceReaderCommand();
        context.put(FILE_NAME, "commandChainProcess.xml");
        final CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
        final InputSource source = (InputSource) context.get(INPUT_SOURCE);
        Assert.assertThat(source, CoreMatchers.notNullValue());
    }

}
