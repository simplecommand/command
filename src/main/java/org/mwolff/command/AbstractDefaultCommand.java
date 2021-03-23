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

package org.mwolff.command;

import org.mwolff.command.interfaces.Command;
import org.mwolff.command.interfaces.CommandTransition;

import static org.mwolff.command.interfaces.CommandTransition.FAILURE;
import static org.mwolff.command.interfaces.CommandTransition.SUCCESS;

/** Default implementation for a command. The command interface offers only the
 * executeCommand method which returns as a default SUSSESS. Event the paramter
 * object equals null, FAILURE is returned.
 *
 * @since 1.5.1 */
public abstract class AbstractDefaultCommand<T extends Object> implements Command<T> {

    /** Executes the command and returns a CommandTransition as feedback.
     * Implementation should save errors in the parameter object.
     *
     * @param parameterObject The parameter object to pass. Should not be finalized.
     * @return CommandTransion.SUCCESS by default, FAILURE if the parameter equals null.
     */
    @Override
    public CommandTransition executeCommand(T parameterObject) {
        if (parameterObject == null) {
            return FAILURE;
        }
        return SUCCESS;
    }
}
