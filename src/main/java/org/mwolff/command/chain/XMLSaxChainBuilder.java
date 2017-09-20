/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>

    Download: https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git

    Copyright (C) 2018 Manfred Wolff and the simple command community

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
    USA
 */

package org.mwolff.command.chain;

import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.sax.ActionListToCommandContainerCommand;
import org.mwolff.command.sax.InputSourceReaderCommand;
import org.mwolff.command.sax.SaxParserCommand;

/**
 * Chain builder parsing an XML file for building chains or process chains.
 *
 * @author Manfred Wolff
 */
public class XMLSaxChainBuilder<T extends Object> implements Command<T>, ProcessCommand<T>, ChainCommand<T> {

    private static final Logger         LOG     = Logger.getLogger(XMLSaxChainBuilder.class);
    private final List<Command<Object>> actions = new ArrayList<>();
    private String xmlFilename;

    public XMLSaxChainBuilder(final String xmlFilename) {
        this.xmlFilename = xmlFilename;
    }
    
    @Override
    public String executeAsProcess(String startCommand, T context) {
        try {
            return buildChain(context).executeAsProcess(startCommand, context);
        } catch (final CommandException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public String executeAsProcess(T context) {
        return null;
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @Override
    public void setProcessID(String processID) {
    }

    @Override
    public void execute(T parameterObject) throws CommandException {
        throw new UnsupportedOperationException("Use executeCommand instead.");
    }

    @SuppressWarnings("unchecked")
    protected CommandContainer<T> buildChain(T parameterObject) throws CommandException {

        GenericParameterObject context = DefaultParameterObject.getInstance();
        context.put(file_name, this.xmlFilename);
        
        InputSourceReaderCommand<GenericParameterObject> inputSourceReaderCommand = new InputSourceReaderCommand<GenericParameterObject>();
        SaxParserCommand<GenericParameterObject> commandSaxParser = new SaxParserCommand<>();
        ActionListToCommandContainerCommand<GenericParameterObject> actionListToCommandContainerCommand = new ActionListToCommandContainerCommand<GenericParameterObject>();
        CommandContainer<T> defaultCommandContainer = new DefaultCommandContainer<T>();
        defaultCommandContainer.addCommand(1, (Command<T>) inputSourceReaderCommand);
        defaultCommandContainer.addCommand(2, (Command<T>) commandSaxParser);
        defaultCommandContainer.addCommand(3, (Command<T>) actionListToCommandContainerCommand);

        CommandTransition result = defaultCommandContainer.executeCommand((T) context);
        if (result == FAILURE) {
            throw new CommandException(context.getAsString(error_string));
        }
        
        DefaultCommandContainer<T> container = (DefaultCommandContainer<T>) context.get(command_container);
        
        return container;
    }

    @Override
    public CommandTransition executeCommand(T parameterObject) {
        try {
            buildChain(parameterObject).executeCommand(parameterObject);
        } catch (CommandException e) {
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        try {
            buildChain(parameterObject).executeCommandAsChain(parameterObject);
        } catch (CommandException e) {
            return FAILURE;
        }
        return SUCCESS;
    }

    @Override
    public boolean executeAsChain(T parameterObject) {
        throw new UnsupportedOperationException("Use executeCommandAsChain instead.");
    }

}
