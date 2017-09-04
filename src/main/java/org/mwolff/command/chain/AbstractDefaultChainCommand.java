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
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;

/**
 * Default implementation for a chain-command. You may use
 * <code>executeAsChain</code> for all executions of the <code>command</code> or
 * <code>commandContainer</code>.
 */
public abstract class AbstractDefaultChainCommand<T extends Object> implements ChainCommand<T> {

    private static final Logger LOG = Logger.getLogger(AbstractDefaultChainCommand.class);

    /**
     * @see org.mwolff.command.Command#execute(java.lang.Object)
     */
    @Override
    public abstract void execute(T context) throws CommandException;


    /**
     * @see org.mwolff.command.chain.ChainCommand#executeAsChain(java.lang.Object)
     */
    @Override
    public boolean executeAsChain(final T context) {
        
        boolean result = true;
        
        try {
            execute(context);
        } catch (final Exception e) {
            AbstractDefaultChainCommand.LOG.info("Chain is aborted.", e);
            result = false;
        }
        return result;
    }

    /**
     * @see org.mwolff.command.Command#executeCommand(java.lang.Object)
     */
    @Override
    public CommandTransition executeCommand(T parameterObject) {
        try {
            execute(parameterObject);
        } catch (CommandException e) {
            return CommandTransition.FAILURE;
        }
        return CommandTransition.SUCCESS;
    }

    /**
     * @see org.mwolff.command.chain.ChainCommand#executeCommandAsChain(java.lang.Object)
     */
    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        boolean result = executeAsChain(parameterObject);
        return result ? CommandTransition.SUCCESS : CommandTransition.DONE;
    }
}
