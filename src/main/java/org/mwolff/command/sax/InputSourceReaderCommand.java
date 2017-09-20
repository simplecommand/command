package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.io.InputStream;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;

public class InputSourceReaderCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    @Override
    public CommandTransition executeCommand(T parameterObject) {

        String filename = parameterObject.getAsString(FILE_NAME.toString());
        if ( ! filename.startsWith("/")) {
            filename = "/" + filename;
        }
        
        final InputStream inputStream = this.getClass()
                .getResourceAsStream(filename);

        if (inputStream == null) {
            parameterObject.put(ERROR_STRING.toString(), "Error reading resource. Resource not found.");
            return FAILURE;
        }
        final InputSource inputSource = new InputSource(inputStream);
        parameterObject.put(INPUT_SOURCE.toString(), inputSource);
        return SUCCESS;
    }

}
