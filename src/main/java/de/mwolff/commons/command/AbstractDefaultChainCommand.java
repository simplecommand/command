/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>
    Download: https://github.com/simplecommand/SimpleCommandFramework


    Copyright (C) 2015 neusta software development

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
package de.mwolff.commons.command;

import org.apache.log4j.Logger;

import de.mwolff.commons.command.iface.ChainCommand;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;

/**
 * Default implementation for a chain-command. You may use
 * <code>executeAsChain</code> for all executions of the <code>command</code> or
 * <code>commandContainer</code>.
 */
public abstract class AbstractDefaultChainCommand<T extends ParameterObject> implements ChainCommand<T> {

    private static final Logger LOG = Logger.getLogger(AbstractDefaultChainCommand.class);

    /**
     * @see de.mwolff.commons.command.iface.Command#execute(de.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public abstract void execute(T context) throws CommandException;

    /**
     * @see de.mwolff.commons.command.iface.Command#executeAsChain(de.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public boolean executeAsChain(T context) {
        boolean result = true;
        try {
            execute(context);
        } catch (final Exception e) {
            AbstractDefaultChainCommand.LOG.info("Chain is aborted.", e);
            result = false;
        }
        return result;
    }
}
