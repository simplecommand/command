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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.chain.XMLChainBuilder;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import java.util.Optional;

public class ProcessTestCommandEndTest {

    @Test
    public void testEndCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final Optional<String> result = xmlChainBuilder.executeAsProcess("END", context);
        assertThat(result, is(Optional.empty()));
        final String contextResult = context.getAsString("result");
        assertThat(contextResult, is("END - "));
    }

    @Test
    public void testEndCommandStandalone() throws Exception {
        final ProcessTestCommandEnd<GenericParameterObject> command = new ProcessTestCommandEnd<>();
        final DefaultParameterObject context = new DefaultParameterObject();
        final Optional<String> result = command.executeAsProcess("END", context);
        assertThat(result, is(Optional.empty()));
    }

    @Test
    void executCommandResultNull() {
        final ProcessTestCommandEnd<GenericParameterObject> command = new ProcessTestCommandEnd<>();
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("result", null);
        CommandTransition result = command.executeCommand(context);
        assertThat(result, is(CommandTransition.SUCCESS));
        final String contextResult = context.getAsString("result");
        assertThat(contextResult, is("null - "));
    }

    @Test
    void executCommandResultElsewhere() {
        final ProcessTestCommandEnd<GenericParameterObject> command = new ProcessTestCommandEnd<>();
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("result", "elsewhere");
        CommandTransition result = command.executeCommand(context);
        assertThat(result, is(CommandTransition.SUCCESS));
        final String contextResult = context.getAsString("result");
        assertThat(contextResult, is("elsewherenull - "));
    }

    @Test
    void executCommandResultEmpty() {
        final ProcessTestCommandEnd<GenericParameterObject> command = new ProcessTestCommandEnd<>();
        final DefaultParameterObject context = new DefaultParameterObject();
        CommandTransition result = command.executeCommand(context);
        assertThat(result, is(CommandTransition.SUCCESS));
        final String contextResult = context.getAsString("result");
        assertThat(contextResult, is("null - "));
    }
}
