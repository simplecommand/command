/**
 * Simple Command Framework.
 * <p>
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 * <p>
 * Download:
 * https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git
 * <p>
 * Copyright (C) 2018 Manfred Wolff and the simple command community
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301
 * USA
 */

package org.mwolff.command.chain;

import org.mwolff.command.Command;
import org.mwolff.command.CommandTransition;

/** ChainCommand interface for the command framework. The behavior of this
 * method
 * is the chain of responsibility pattern.
 *
 * Commands may act with generic command contexts. A context actually is a
 * parameter object which passes information along the whole chain.
 *
 * @author Manfred Wolff */
public interface ChainCommand<T extends Object> extends Command<T> {

    /** @since 1.5.0
     * @param parameterObject The parameter object to pass.
     * @return SUCCESS is the next chain should overtake, ABORT otherwise. */
    CommandTransition executeCommandAsChain(T parameterObject);
}