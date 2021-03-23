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

package org.mwolff.command.chain;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mwolff.command.interfaces.ChainCommand;
import org.mwolff.command.interfaces.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mwolff.command.interfaces.CommandTransition.NEXT;
import static org.mwolff.command.interfaces.CommandTransition.SUCCESS;

public class ChainCommandTest {

    @Test
    public void testInterfaceDefaultExecuteCommandAsChain() {

        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new AbstractDefaultChainCommand<GenericParameterObject>() {

            @Override
            public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
                return SUCCESS;
            }

        };

        CommandTransition transition = command.executeCommand(context);
        assertThat(transition, CoreMatchers.is(SUCCESS));
        transition = command.executeCommandAsChain(context);
        assertThat(transition, CoreMatchers.is(NEXT));
    }

    @Test
    public void testInterfaceDefaultExecuteCommandAsChainFAILED() {

        final ChainCommand<GenericParameterObject> command = new AbstractDefaultChainCommand<GenericParameterObject>() {

            @Override
            public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
                return SUCCESS;
            }

        };

        CommandTransition transition = command.executeCommand(null);
        assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
        transition = command.executeCommandAsChain(null);
        assertThat(transition, CoreMatchers.is(CommandTransition.NEXT));
    }

}
