package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransition;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxParserCommand extends AbstractDefaultCommand<SaxParameterObject> {

    private static final Logger LOG = Logger.getLogger(SaxParserCommand.class);

    @Override
    public CommandTransition executeCommand(SaxParameterObject parameterObject) {

        try {
            final InputSource inputSource = (InputSource) parameterObject.get(INPUT_SOURCE);

            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            final ActionContentHandler handler = new ActionContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            parameterObject.put(ACTION_LIST, handler.getActions());

        } catch (IOException | SAXException e) {
            SaxParserCommand.LOG.error(e);
            parameterObject.put(ERROR_STRING, e.getMessage());
            return FAILURE;
        }
        return SUCCESS;
    }

}
