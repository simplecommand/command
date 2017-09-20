package org.mwolff.command.sax;

import java.io.IOException;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParserCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    @Override
    public CommandTransition executeCommand(T parameterObject) {

        try {
            final InputSource inputSource = (InputSource) parameterObject.get(GlobalCommandConstants.input_source);

            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            final ActionContentHandler handler = new ActionContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            parameterObject.put(GlobalCommandConstants.action_list, handler.getActions());

        } catch (IOException | SAXException e) {
            parameterObject.put(GlobalCommandConstants.error_string, e.getMessage());
            return CommandTransition.FAILURE;
        }
        return CommandTransition.SUCCESS;
    }

}
