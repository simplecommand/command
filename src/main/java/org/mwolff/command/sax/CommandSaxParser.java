package org.mwolff.command.sax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class CommandSaxParser extends AbstractDefaultCommand<GenericParameterObject>{
    
    public static String file_name = "file.name";
    public static String action_list = "action.list";
    public static String error_string = "error.string";
    
    public final static CommandSaxParser getInstance() {
        return new CommandSaxParser();
    }
    
    @Override
    public CommandTransition executeCommand(GenericParameterObject parameterObject) {
        
        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            final InputStream inputStream = this.getClass().getResourceAsStream("/" + parameterObject.getAsString(file_name));
            
            InputSource inputSource = new InputSource(inputStream);

            ActionContentHandler handler = new ActionContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            parameterObject.put(action_list, handler.getActions());

        } catch (IOException | SAXException e) {
            System.out.println(e.getMessage());
            parameterObject.put(error_string, e.getMessage());
            return CommandTransition.FAILURE;
        }
        return CommandTransition.SUCCESS;
    }

}
