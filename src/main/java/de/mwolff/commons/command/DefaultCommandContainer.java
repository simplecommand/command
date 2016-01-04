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
package de.mwolff.commons.command;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.Context;

/**
 * CommandContainer that holds Command-objects. Should have the same behavior as
 * a command (Composite Pattern).
 */
public class DefaultCommandContainer<T extends Context> implements CommandContainer<T> {

    private final Map<Integer, Command<T>> commandList = new TreeMap<Integer, Command<T>>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            // First wins if there are two commands with the same
            // priority
            if (o1.intValue() >= o2.intValue()) {
                return 1;
            } else {
                return -1;
            } // returning 0 would merge keys
        }
    });

    /**
     * @see de.mwolff.commons.command.iface.CommandContainer#addCommand(de.mwolff.commons.command.iface.Command)
     */
    @Override
    public void addCommand(Command<T> command) {
        commandList.put(Integer.valueOf(0), command);
    }

    /**
     * @see de.mwolff.commons.command.iface.CommandContainer#addCommand(int,
     *      de.mwolff.commons.command.iface.Command)
     */
    @Override
    public void addCommand(int priority, Command<T> command) {
        commandList.put(Integer.valueOf(priority), command);
    }

    /**
     * @see de.mwolff.commons.command.iface.Command#execute(de.mwolff.commons.command.iface.Context)
     */
    @Override
    public void execute(T context) throws CommandException {
        for (final Command<T> command : commandList.values()) {
            command.execute(context);
        }
    }

    /**
     * @see de.mwolff.commons.command.iface.Command#executeAsChain(de.mwolff.commons.command.iface.Context)
     */
    @Override
    public boolean executeAsChain(T context) {
        boolean result = true;
        for (final Command<T> command : commandList.values()) {
            result = command.executeAsChain(context);
            if (!result) {
                break;
            }
        }
        return result;
    }

    /**
     * @see de.mwolff.commons.command.iface.Command#executeAsProcess(de.mwolff.commons.command.iface.Context)
     */
    @Override
    public String executeAsProcess(String startCommand, T context) {

        Command<T> command = null;

        for (final Command<T> actcommand : commandList.values()) {
            String processID = actcommand.getProcessID();
            if (processID.equals(startCommand)) {
                command = actcommand;
                break;
            }
        }

        if (command == null)
            return null;

        String next = command.executeAsProcess(startCommand, context);

       return executeAsProcess(next, context);
    }

    @Override
    public String getProcessID() {
        return null;
    }
}
