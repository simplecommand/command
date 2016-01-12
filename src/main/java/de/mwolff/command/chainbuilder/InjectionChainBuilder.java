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
package de.mwolff.command.chainbuilder;

import java.util.ArrayList;
import java.util.List;

import de.mwolff.commons.command.DefaultCommandContainer;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.Context;

/**
 * Generic chain builder for configuration with the spring framework.
 */
public class InjectionChainBuilder<T extends Context> implements ChainBuilder<T> {

    private List<Command<T>> commands = new ArrayList<Command<T>>();

    /**
     * Injection point for the dependency framework.
     *
     * @param commands
     */
    public void setCommands(final List<Command<T>> commands) {
        this.commands = commands;
    }

    /**
     * @see de.mwolff.command.chainbuilder.ChainBuilder#executeAsChain(de.mwolff.commons.command.iface.Context)
     */
    @Override
    public boolean executeAsChain(final T context)  {
        return buildChain().executeAsChain(context);
    }

    /**
     * Builder method.
     * 
     * @return
     */
    private CommandContainer<T> buildChain() {

        final CommandContainer<T> commandContainer = new DefaultCommandContainer<T>();
        for (final Command<T> command : commands) {
            commandContainer.addCommand(command);
        }
        return commandContainer;
    }
}
