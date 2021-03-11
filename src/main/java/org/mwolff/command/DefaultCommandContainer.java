/* Simple Command Framework.
 *
 *          Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2018 - 2020 Manfred Wolff and the simple command community
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

package org.mwolff.command;

import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.process.ProcessCommand;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static org.mwolff.command.CommandTransition.*;

/**
 * CommandContainer that holds Command-objects. Should have the same behavior
 * as
 * a command (Composite Pattern).
 *
 * @author Manfred Wolff
 */
public class DefaultCommandContainer<T> implements CommandContainer<T> {

    private final Map<Integer, Command<T>> commandList =
            new TreeMap<>((final Integer o1, final Integer o2) -> {
                if (o1 >= o2) {
                    return 1;
                } else {
                    return -1;
                }
            });

    /**
     * @see org.mwolff.command.CommandContainer#addCommand(org.mwolff.command.Command)
     */
    @Override
    public CommandContainer<T> addCommand(final Command<T> command) {
        commandList.put(0, command);
        return this;
    }

    /**
     * @see org.mwolff.command.CommandContainer#addCommand(int,
     * org.mwolff.command.Command)
     */
    @Override
    public CommandContainer<T> addCommand(final int priority, final Command<T> command) {
        commandList.put(priority, command);
        return this;
    }

    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {

        CommandTransition result = NEXT;
        for (final Command<T> command : commandList.values()) {
            result = ((ChainCommand<T>) command).executeCommandAsChain(parameterObject);
            if ((result == DONE) || (result == FAILURE)) {
                break;
            }
        }
        return result;
    }

    /**
     * @see org.mwolff.command.process.ProcessCommand#executeAsProcess(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public String executeAsProcess(final String startCommand, final T context) {

        return Optional.ofNullable(getCommandByProcessID(startCommand))
                .flatMap(cmd ->
                    Optional.ofNullable(cmd.executeAsProcess(context))
                        .map(cmd::findNext))
                        .map(nextCommand -> this.executeAsProcess(nextCommand, context))
                .orElse(null);
    }

    @Override
    public ProcessCommand<T> getCommandByProcessID(final String proceddID) {

        ProcessCommand<T> command = null;

        for (final Command<T> actcommand : commandList.values()) {
            final String actualProcessId = ((ProcessCommand<T>) actcommand).getProcessID();
            if (actualProcessId.equals(proceddID)) {
                command = (ProcessCommand<T>) actcommand;
                break;
            }
        }
        return command;
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @Override
    public void setProcessID(final String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }

    @Override
    public String executeAsProcess(T context) {
        return null;
    }

    @Override
    public CommandTransition executeCommand(T parameterObject) {

        CommandTransition transition = SUCCESS;

        for (final Command<T> command : commandList.values()) {
            transition = command.executeCommand(parameterObject);
            if (transition.equals(FAILURE)) {
                break;
            }
        }

        return transition;
    }
}
