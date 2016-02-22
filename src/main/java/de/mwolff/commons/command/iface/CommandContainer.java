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
package de.mwolff.commons.command.iface;

/**
 * Interface of a command container.
 */
public interface CommandContainer<T extends Context> extends Command<T>, ChainCommand<T>, ProcessCommand<T> {

    /**
     * Adds a <code>Command</code> to the list. Because a
     * <code>CommandContainer</code> is a <code>Command</code> you can add
     * <code>CommandContainer</code> objects as well.
     *
     * @param command
     */
    void addCommand(Command<T> command);

    /**
     * Adds a <code>Command</code> to the list via priority. Because a
     * <code>CommandContainer</code> is a <code>Command</code> you can add
     * <code>CommandContainer</code> objects as well.
     *
     * @param priority
     * @param command
     */
    void addCommand(int priority, Command<T> command);

    /**
     * Gets the command with the certain processID.
     * 
     * @param processID
     * @return The command with the certain processID
     */
    Command<T> getCommandByProcessID(String processID);
}