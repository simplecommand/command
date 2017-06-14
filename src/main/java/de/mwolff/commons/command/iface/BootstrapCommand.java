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
 * Command interface for the command framework. Commands may act with generic
 * command contexts.
 */
public interface BootstrapCommand<T extends ParameterObject> extends Command<T> {

    /**
     * Executes a command as a chain. Best way to execute a command chain is to
     * execute it as a chain because exceptions are automatically handled.
     *
     * @param context
     * @return False if there is an error or the whole task is completed. True
     *         if the next command should overtake.
     */
    boolean executeAsChain(T context);

    /**
     * Gets the priority of this command.
     * @return
     */
    int getPriority();
}