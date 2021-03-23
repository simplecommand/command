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

public enum CommandTransition {

    /** @since 1.5.0
     *        Transition for an execute Method.
     *
     *        State can be:
     *
     *        SUCCESS : Execution was Successfully done. Used for Command-Mode
     *        DONE : For CoR: The work is successfully done, the chain can be aborted.
     *        NEXT : For CoR: The work is not done, next chain should overtake.
     *        FAILURE : An fatal error has occurred. The chain should be aborted
     *
     * @since 1.5.0 */
    SUCCESS, FAILURE, NEXT, DONE

}
