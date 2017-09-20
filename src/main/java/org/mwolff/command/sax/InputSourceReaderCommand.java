package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import java.io.InputStream;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;

public class InputSourceReaderCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    public static String file_name    = "inputsourcereader.file.name";
    public static String input_source = "inputsourcereader.input.source";
    public static String error_string = "global.error.string";

    @Override
    public CommandTransition executeCommand(T parameterObject) {

        final InputStream inputStream = this.getClass()
                .getResourceAsStream("/" + parameterObject.getAsString(file_name));
        if (inputStream == null) {
            parameterObject.put(error_string, "Error reading resource. Resource not found.");
            return FAILURE;
        }
        InputSource inputSource = new InputSource(inputStream);
        parameterObject.put(input_source, inputSource);
        return SUCCESS;
    }

}
