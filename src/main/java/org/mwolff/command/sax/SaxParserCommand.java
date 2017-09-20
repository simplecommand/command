package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParserCommand<T extends GenericParameterObject> extends AbstractDefaultCommand<T> {

    private static final Logger         LOG   = Logger.getLogger(SaxParserCommand.class);
    
    @Override
    public CommandTransition executeCommand(T parameterObject) {

        try {
            final InputSource inputSource = (InputSource) parameterObject.get(INPUT_SOURCE.toString());

            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            final ActionContentHandler handler = new ActionContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            parameterObject.put(ACTION_LIST.toString(), handler.getActions());

        } catch (IOException | SAXException e) {
            LOG.error(e);
            parameterObject.put(ERROR_STRING.toString(), e.getMessage());
            return FAILURE;
        }
        return SUCCESS;
    }

}
