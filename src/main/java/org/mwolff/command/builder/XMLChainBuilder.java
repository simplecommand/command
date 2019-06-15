/*        Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
 *
 *         This library is free software; you can redistribute it and/or
 *         modify it under the terms of the GNU Lesser General Public
 *         License as published by the Free Software Foundation; either
 *         version 2.1 of the License, or (at your option) any later version.
 *
 *         This library is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         Lesser General Public License for more details.
 *
 *         You should have received a copy of the GNU Lesser General Public
 *         License along with this library; if not, write to the Free Software
 *         Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *         02110-1301 USA
 */
package org.mwolff.command.builder;

import org.apache.log4j.Logger;
import org.mwolff.command.*;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.sax.ActionListToCommandContainerCommand;
import org.mwolff.command.sax.InputSourceReaderCommand;
import org.mwolff.command.sax.SaxParameterObject;
import org.mwolff.command.sax.SaxParserCommand;

import java.util.Optional;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

/** Chain builder parsing an XML file for building chains or process chains.
 *
 * @author Manfred Wolff */
public class XMLChainBuilder<T extends Object> implements Command<T>, ProcessCommand<T>, ChainCommand<T> {

    private static final Logger LOG = Logger.getLogger(XMLChainBuilder.class);
    private final String        xmlFilename;

    public XMLChainBuilder(final String xmlFilename) {
        this.xmlFilename = xmlFilename;
    }

    @Override
    public Optional<String> executeAsProcess(String startCommand, T context) {
        try {
            return buildChain().executeAsProcess(startCommand, context);
        } catch (final CommandException e) {
            LOG.error(e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> executeAsProcess(T context) {
        throw new UnsupportedOperationException("Use executeAsProcess(String start, T context");
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @Override
    public void setProcessID(String processID) {
        throw new UnsupportedOperationException("Chainbuilder has no process id.");
    }

    @SuppressWarnings("unchecked")
    protected CommandContainer<T> buildChain() throws CommandException {

        final SaxParameterObject context = new SaxParameterObject();
        context.put(FILE_NAME.toString(), this.xmlFilename);

        final InputSourceReaderCommand inputSourceReaderCommand = new InputSourceReaderCommand();
        final SaxParserCommand commandSaxParser = new SaxParserCommand();
        final ActionListToCommandContainerCommand actionListToCommandContainerCommand = new ActionListToCommandContainerCommand();
        final CommandContainer<SaxParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        defaultCommandContainer.addCommand(1, inputSourceReaderCommand);
        defaultCommandContainer.addCommand(2, commandSaxParser);
        defaultCommandContainer.addCommand(3, actionListToCommandContainerCommand);

        final CommandTransition result = defaultCommandContainer.executeCommand(context);

        if (result == FAILURE) {
            throw new CommandException(context.getAsString(ERROR_STRING.toString()));
        }

        return (CommandContainer<T>) context.get(COMMAND_CONTAINER.toString());

    }

    @Override
    public CommandTransition executeCommand(T parameterObject) {
        try {
            buildChain().executeCommand(parameterObject);
        } catch (final CommandException e) {
            LOG.error(e);
            return FAILURE;
        }
        return SUCCESS;
    }

    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        try {
            buildChain().executeCommandAsChain(parameterObject);
        } catch (final CommandException e) {
            LOG.error(e);
            return FAILURE;
        }
        return DONE;
    }
}
