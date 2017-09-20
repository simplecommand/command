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

import java.util.ArrayList;
import java.util.List;

import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.process.ProcessCommand;

/**
 * Generic chain builder for configuration with the spring framework.
 */
public class InjectionChainBuilder<T extends Object> implements Command<T>, ProcessCommand<T>, ChainCommand<T> {

    private List<Command<T>> commands = new ArrayList<>();

    /**
     * Builds the chain for this builder.
     * @return Returns the command container build.
     */
    protected CommandContainer<T> buildChain() {

        final CommandContainer<T> commandContainer = new DefaultCommandContainer<>();
        for (final Command<T> command : commands) {
            commandContainer.addCommand(command);
        }
        return commandContainer;
    }

    /**
     * @see org.mwolff.command.Command#execute(java.lang.Object)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void execute(final T context) throws CommandException {
        buildChain().execute(context);
    }

    /**
     * @see org.mwolff.command.chain.ChainCommand#executeAsChain(java.lang.Object)
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean executeAsChain(final T context) {
        return buildChain().executeAsChain(context);
    }

    /**
     * @see org.mwolff.command.chain.ChainCommand#executeCommandAsChain(java.lang.Object)
     */
    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        return buildChain().executeCommandAsChain(parameterObject);
    }

    /**
     * @see org.mwolff.command.process.ProcessCommand#executeAsProcess(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        return buildChain().executeAsProcess(startCommand, context);
    }

    /**
     * @see org.mwolff.command.process.ProcessCommand#getProcessID()
     */
    @Override
    public String getProcessID() {
        return null;
    }

    /**
     * Sets the list of commands.
     *
     * @param commands
     *            Command to set from the injection framework.
     */
    public void setCommands(final List<Command<T>> commands) {
        this.commands = commands;
    }

    /**
     * @see org.mwolff.command.process.ProcessCommand#setProcessID(java.lang.String)
     */
    @Override
    public void setProcessID(final String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }

    /**
     * @see org.mwolff.command.process.ProcessCommand#executeAsProcess(java.lang.Object)
     */
    @Override
    public String executeAsProcess(T context) {
        return null;
    }

}
