package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import java.io.InputStream;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;

public class InputSourceReaderCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    @Override
    public CommandTransition executeCommand(T parameterObject) {

        String filename = parameterObject.getAsString(GlobalCommandConstants.file_name);
        if (!filename.startsWith("/")) {
            filename = "/" + filename;
        }

        final InputStream inputStream = this.getClass().getResourceAsStream(filename);
        if (inputStream == null) {
            parameterObject.put(GlobalCommandConstants.error_string, "Error reading resource. Resource not found.");
            return FAILURE;
        }
        final InputSource inputSource = new InputSource(inputStream);
        parameterObject.put(GlobalCommandConstants.input_source, inputSource);
        return SUCCESS;
    }

}
