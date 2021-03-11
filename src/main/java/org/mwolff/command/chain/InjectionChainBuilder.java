/**
 * Simple Command Framework.
 * <p>
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 * <p>
 * Download:
 * https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git
 * <p>
 * Copyright (C) 2018 Manfred Wolff and the simple command community
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301
 * USA
 */

package org.mwolff.command.chain;

import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.process.ProcessCommand;

import java.util.ArrayList;
import java.util.List;

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
    public String executeAsProcess(final String startCommand, final T context) {
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
    public String executeAsProcess(T context) {
        throw new UnsupportedOperationException("Use executeAsProcess(String start, T context");
    }
}
