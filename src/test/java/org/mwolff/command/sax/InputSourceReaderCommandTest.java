package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;

public class InputSourceReaderCommandTest {

    @Test
    public void testInvalidFilenName() throws Exception {
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        final InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<>();
        context.put(GlobalCommandConstants.file_name, "invalidFile.xml");
        final CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
        final String error = context.getAsString(GlobalCommandConstants.error_string);
        Assert.assertThat(error, CoreMatchers.is("Error reading resource. Resource not found."));
    }

    @Test
    public void testValidFilenName() throws Exception {
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        final InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<>();
        context.put(GlobalCommandConstants.file_name, "commandChainProcess.xml");
        final CommandTransition result = inputSourceReaderCommand.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
        final InputSource source = (InputSource) context.get(GlobalCommandConstants.input_source);
        Assert.assertThat(source, CoreMatchers.notNullValue());
    }

}
