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

import org.apache.log4j.Logger;
import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.sax.CommandSaxParser;

/**
 * Chain builder parsing an XML file for building chains or process chains.
 *
 * @author Manfred Wolff
 */
public class XMLSaxChainBuilder<T extends Object> implements Command<T>, ProcessCommand<T>, ChainCommand<T> {

    private static final Logger         LOG   = Logger.getLogger(XMLSaxChainBuilder.class);

    @Override
    public boolean executeAsChain(T parameterObject) {
        CommandContainer<T> chain = null;
        try {
            chain = buildChain(parameterObject);
        } catch (final Exception e) {
            LOG.error("Operation aborted via command implementation.", e);
            return false;
        }
        return chain.executeAsChain(parameterObject);
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

    @SuppressWarnings("deprecation")
    @Override
    public void execute(T parameterObject) throws CommandException {
        try {
            buildChain(parameterObject).execute(parameterObject);
        } catch (final Exception e) {
            // Just log, do nothing else
            LOG.error("Error while executing chain.", e);
        }
    }
    
    protected CommandContainer<T> buildChain(T parameterObject) throws CommandException {
        CommandSaxParser<GenericParameterObject> commandSaxParser = new CommandSaxParser<>();
        CommandTransition result = commandSaxParser.executeCommand((GenericParameterObject) parameterObject);
        if (result == CommandTransition.FAILURE) {
            throw new CommandException("XML Document could not created");
        }
        return new DefaultCommandContainer<>();
    }
    
    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        final boolean result = executeAsChain(parameterObject);
        return result ? CommandTransition.SUCCESS : CommandTransition.DONE;
    }

}
