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
package org.mwolff.command.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.process.ProcessCommand;

public class InjectionChainBuilder<T extends Object> implements Command<T>, ProcessCommand<T>, ChainCommand<T> {

    private List<Command<T>> commands = new ArrayList<>();

    @Override
    public CommandTransition executeCommand(T parameterObject) {
        return buildChain().executeCommand(parameterObject);
    }

    /** @see org.mwolff.command.chain.ChainCommand#executeCommandAsChain(java.lang.Object) */
    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        return buildChain().executeCommandAsChain(parameterObject);
    }

    /** @see org.mwolff.command.process.ProcessCommand#executeAsProcess(java.lang.String,
     *      java.lang.Object) */
    @Override
    public Optional<String> executeAsProcess(final String startCommand, final T context) {
        return buildChain().executeAsProcess(startCommand, context);
    }

    /** Builds the chain for this builder.
     *
     * @return Returns the command container build. */
    protected CommandContainer<T> buildChain() {
        final CommandContainer<T> commandContainer = new DefaultCommandContainer<>();
        commands.forEach(commandContainer::addCommand);
        return commandContainer;
    }

    /** @see org.mwolff.command.process.ProcessCommand#getProcessID() */
    @Override
    public String getProcessID() {
        return null;
    }

    /** Sets the list of commands.
     *
     * @param commands
     *            Command to set from the injection framework. */
    public void setCommands(final List<Command<T>> commands) {
        this.commands = commands;
    }

    /** @see org.mwolff.command.process.ProcessCommand#setProcessID(java.lang.String) */
    @Override
    public void setProcessID(final String processID) {
        throw new UnsupportedOperationException("ProcessID cannot be set on Container.");
    }

    /** @see org.mwolff.command.process.ProcessCommand#executeAsProcess(java.lang.Object) */
    @Override
    public Optional<String> executeAsProcess(T context) {
        throw new UnsupportedOperationException("Use executeAsProcess(String start, T context");
    }
}
