/** Simple Command Framework.
 * 
 * Framework for easy building software that fits the SOLID principles.
 * 
 * @author Manfred Wolff <m.wolff@neusta.de>
 * 
 *         Download:
 *         https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git
 * 
 *         Copyright (C) 2018 Manfred Wolff and the simple command community
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

import static org.mwolff.command.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AbstractDefaultCommandTest {

    final class Testclass extends AbstractDefaultCommand<String> {
    }

    @Test
    @DisplayName("Returns SUCCESS if context is not null.")
    void testDefaultExecuteCommandSuccess() {
        final Testclass testclass = new Testclass();
        final CommandTransition result = testclass.executeCommand("notnull");
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
    }

    @Test
    @DisplayName("Returns FAILURE if context is null.")
    void testDefaultExecuteCommandFailed() {
        final Testclass testclass = new Testclass();
        final CommandTransition result = testclass.executeCommand(null);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
    }

}
