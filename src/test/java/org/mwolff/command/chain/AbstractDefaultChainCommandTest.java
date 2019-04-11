/*         Simple Command Framework.
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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransition.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;

public class AbstractDefaultChainCommandTest {

    final class TestClassSuccess extends AbstractDefaultChainCommand<String> {
        @Override
        public CommandTransition executeCommand(String parameterObject) {
            return SUCCESS;
        }
    }

    final class TestClassFailure extends AbstractDefaultChainCommand<String> {
        @Override
        public CommandTransition executeCommand(String parameterObject) {
            return FAILURE;
        }
    }
    
    @Test
    @DisplayName("Default Behavior: Asserts that the chain results DONE if underlying executeCommand() results FAILURE.")
    void executeAsChainNull() throws Exception {
        final TestClassFailure command = new TestClassFailure();
        final CommandTransition result = command.executeCommandAsChain(null);
        assertThat(result, is(CommandTransition.DONE));
    }

    @Test
    @DisplayName("Default Behavior: Asserts that the chain results NEXT if underlying executeCommand() results SUCCESS.")
    void executeAsChainNotNull() throws Exception {
        final TestClassSuccess command = new TestClassSuccess();
        final CommandTransition result = command.executeCommandAsChain("Hello chain!");
        assertThat(result, is(CommandTransition.NEXT));
    }
}
