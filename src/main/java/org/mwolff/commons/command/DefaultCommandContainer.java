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
package org.mwolff.commons.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.mwolff.commons.command.iface.ChainCommand;
import org.mwolff.commons.command.iface.Command;
import org.mwolff.commons.command.iface.CommandContainer;
import org.mwolff.commons.command.iface.CommandException;
import org.mwolff.commons.command.iface.ParameterObject;
import org.mwolff.commons.command.iface.ProcessCommand;

/**
 * CommandContainer that holds Command-objects. Should have the same behavior as
 * a command (Composite Pattern).
 */
public class DefaultCommandContainer<T extends ParameterObject> implements CommandContainer<T> {

    private static final Logger            LOG         = Logger.getLogger(DefaultCommandContainer.class);

    private final Map<Integer, Command<T>> commandList = new TreeMap<>((final Integer o1, final Integer o2) -> {
                                                           // First wins if
                                                           // there are two
                                                           // commands with the
                                                           // same priority
                                                           if (o1.intValue() >= o2.intValue()) {
                                                               return 1;
                                                           } else {
                                                               return -1;
                                                           }                                                             // returning
                                                                                                                         // 0
                                                                                                                         // would
                                                                                                                         // merge
                                                                                                                         // keys
                                                       });

    /**
     * @see org.mwolff.commons.command.iface.CommandContainer#addCommand(org.mwolff.commons.command.iface.Command)
     */
    @Override
    public CommandContainer<T> addCommand(final Command<T> command) {
        commandList.put(Integer.valueOf(0), command);
        return this;
    }

    /**
     * @see org.mwolff.commons.command.iface.CommandContainer#addCommand(int,
     *      org.mwolff.commons.command.iface.Command)
     */
    @Override
    public CommandContainer<T> addCommand(final int priority, final Command<T> command) {
        commandList.put(Integer.valueOf(priority), command);
        return this;
    }

    /**
     * @see org.mwolff.commons.command.iface.Command#execute(org.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public void execute(final T context) throws CommandException {
        for (final Command<T> command : commandList.values()) {
            try {
                command.execute(context);
            } catch (final Exception exception) {
                // Just log, do nothing else
                DefaultCommandContainer.LOG.error("Error while executing chain.", exception);
            }
        }
    }

    @Override
    public void executeOnly(T context) {
        for (final Command<T> command : commandList.values()) {
            command.executeOnly(context);
        }
    }

    /**
     * @see org.mwolff.commons.command.iface.Command#executeAsChain(org.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public boolean executeAsChain(final T context) {
        boolean result = true;
        for (final Command<T> command : commandList.values()) {
            result = ((ChainCommand<T>) command).executeAsChain(context);
            if (!result) {
                break;
            }
        }
        return result;
    }

    /**
     * @see org.mwolff.commons.command.iface.Command#executeAsProcess(org.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public String executeAsProcess(final String startCommand, final T context) {

        Command<T> command;

        command = getCommandByProcessID(startCommand);

        if (command == null) {
            return null;
        }

        // Special Node ENDE
        String next = ((ProcessCommand<T>) command).executeAsProcess(context);
        DefaultCommandContainer.LOG.info("Returnvalue    = ##> " + next);
        // Special Node ENDE
        if ("END".equals(next)) {
            // do first nothing
        } else {
            next = ((ProcessCommand<T>) command).findNext(next);
        }
        DefaultCommandContainer.LOG.info("Next ProcessID = --> " + next);

        if (next == null) {
            return null;
        }
        if ("END".equals(next)) {
            return "END";
        }

        // Recursion until next == null
        return executeAsProcess(next, context);
    }

    @Override
    public Command<T> getCommandByProcessID(final String proceddID) {

        Command<T> command = null;

        for (final Command<T> actcommand : commandList.values()) {
            final String actualProcessId = ((ProcessCommand<T>) actcommand).getProcessID();
            if (actualProcessId.equals(proceddID)) {
                command = actcommand;
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
}
