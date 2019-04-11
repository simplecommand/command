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
package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class VerySimpleTestCommandTest {

    @Test
    void executeCommand() {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        VerySimpleTestCommand<GenericParameterObject> verySimpleTestCommand = new VerySimpleTestCommand<>();
        CommandTransition result = verySimpleTestCommand.executeCommand(context);
        String resultString = context.getAsString("priority");
        assertThat(result, is(CommandTransition.SUCCESS));
        assertThat(resultString, is("S-"));
    }
}
