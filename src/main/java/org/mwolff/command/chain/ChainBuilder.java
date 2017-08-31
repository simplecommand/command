/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>
    
    Download: https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git

    Copyright (C) 2018 Manfred Wolff and the simple command community

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

import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandException;
import org.mwolff.command.process.ProcessCommand;

/**
 * A chain builder interface to build chains via configuration. Known builder so
 * far are the <code>InjectionChainBuilder</code> and the
 * <code>XMLChainBuilder</code>.
 * 
 * The builder takes information out of meta data and builds the chain of
 * commands.
 * 
 * @author Manfred Wolff
 */
public interface ChainBuilder<T extends Object> extends Command<T>, ProcessCommand<T>, ChainCommand<T> {

    /**
     * Factory method to build the chain. The result is a
     * <code>CommandContainer</code> which can be executed.
     *
     * @throws CommandException
     *             if an error occurs.
     * @return A <code>CommandContainer</code> that holds all
     *         <code>Command</code>-Objects.
     */
    CommandContainer<T> buildChain() throws CommandException;
}