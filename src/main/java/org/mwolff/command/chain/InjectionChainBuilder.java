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
package org.mwolff.command.chain;

import java.util.ArrayList;
import java.util.List;

import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandException;
import org.mwolff.command.DefaultCommandContainer;

/**
 * Generic chain builder for configuration with the spring framework.
 */
public class InjectionChainBuilder<T extends Object> implements ChainBuilder<T> {

    private List<Command<T>> commands = new ArrayList<>();

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ChainBuilder#buildChain()
     */
    @Override
    public CommandContainer<T> buildChain() {

        final CommandContainer<T> commandContainer = new DefaultCommandContainer<>();
        for (final Command<T> command : commands) {
            commandContainer.addCommand(command);
        }
        return commandContainer;
    }


    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.Command#execute(Object)
     */
    @Override
    public void execute(final T context) throws CommandException {
        buildChain().execute(context);
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ChainCommand#executeAsChain(Object)
     */
    @Override
    public boolean executeAsChain(final T context) {
        return buildChain().executeAsChain(context);
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#executeAsProcess(java.lang.String, org.mwolff.commons.command.iface.Object)
     */
    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        return buildChain().executeAsProcess(startCommand, context);
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#getProcessID()
     */
    @Override
    public String getProcessID() {
        return null;
    }

    /**
     * Sets the list of commands.
     * @param commands Command to set from the injection framework.
     */
    public void setCommands(final List<Command<T>> commands) {
        this.commands = commands;
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#setProcessID(java.lang.String)
     */
    @Override
    public void setProcessID(final String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }
}
