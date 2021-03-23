/** Simple Command Framework.
 *
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Download:
 *         https://github.com/simplecommand/command.git
 *
 *         Copyright (C) 2018-2021 Manfred Wolff and the simple command community
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
 *         02110-1301
 *         USA */

package org.mwolff.command.interfaces;

/** Interface of a command container. A command container implements all command
 * interfaces. So you can execute commandContainer as usual commands. Actually
 * this implements the composite pattern. So you can mix commands and command
 * container to build chains.
 *
 * @author Manfred Wolff */
public interface CommandContainer<T extends Object> extends ChainCommand<T>, ProcessCommand<T> {

    /** Adds a <code>Command</code> to the list. Because a
     * <code>CommandContainer</code> is a <code>Command</code> you can add
     * <code>CommandContainer</code> objects as well.
     *
     * @param parameterObject
     *            The command to add.
     * @return this */
    CommandContainer<T> addCommand(Command<T> parameterObject);

    /** Adds a <code>Command</code> to the list via priority. Because a
     * <code>CommandContainer</code> is a <code>Command</code> you can add
     * <code>CommandContainer</code> objects as well.
     *
     * @param priority
     *            A priority. If two commands has the same priority the first
     *            wins.
     * @param command
     *            The command to add.
     * @return this */
    CommandContainer<T> addCommand(int priority, Command<T> command);

    /** Gets the command with the certain processID.
     *
     * @param processID
     *            The id to find the command.
     * @return The command with the certain processID */
    Command<T> getCommandByProcessID(String processID);
}