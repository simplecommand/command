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

/** Command interface for the command framework.
 *
 * Commands may act with generic command context. A context actually is a
 * parameter object which passes information along the whole chain.
 *
 * @author Manfred Wolff */
@FunctionalInterface
public interface Command<T extends Object> {

    /** Executes the command. The command can have the result SUCCESS if
     * everything is fine or FAILURE if an error occurred.
     *
     * @since 1.5.0
     * @param parameterObject
     *            The parameter object to pass.
     * @return FAILURE if something went wrong, SUCCESS otherwise. */
    CommandTransition executeCommand(T parameterObject);
}