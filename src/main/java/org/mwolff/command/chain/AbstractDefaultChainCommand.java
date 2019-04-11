/*        Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
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
package org.mwolff.command.chain;

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.CommandTransition;

/** Default implementation for a chain-command. You may use
 * <code>executeAsChain</code> for all executions of the <code>command</code> or
 * <code>commandContainer</code>. */
public abstract class AbstractDefaultChainCommand<T extends Object> extends AbstractDefaultCommand<T>
        implements ChainCommand<T> {

    /** @see org.mwolff.command.chain.ChainCommand#executeCommandAsChain(java.lang.Object) */
    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        final CommandTransition result = executeCommand(parameterObject);
        if (result == CommandTransition.SUCCESS) {
            return CommandTransition.NEXT;
        }
        return CommandTransition.DONE;
    }
}
