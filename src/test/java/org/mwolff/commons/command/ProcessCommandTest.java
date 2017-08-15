/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>
    Download: https://github.com/simplecommand/SimpleCommandFramework


    Copyright (C) 2015 neusta software development

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
    USA
 */
package org.mwolff.commons.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mwolff.commons.command.iface.CommandException;
import org.mwolff.commons.command.iface.ParameterObject;
import org.mwolff.commons.command.iface.ProcessCommand;
import org.mwolff.commons.command.iface.Transition;
import org.mwolff.commons.command.samplecommands.ProcessTestCommandEnd;
import org.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class ProcessCommandTest {

    @Test
    public void executeAsProcessSimpleTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        final GenericParameterObject context = new DefaultParameterObject();
        final String result = processTestStartCommand.executeAsProcess("", context);
        final String processflow = context.getAsString("result");
        assertEquals("Start - ", processflow);
        assertEquals("OK", result);
    }
    
    @Test
    public void testExecuteOnlyStart() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        processTestStartCommand.executeOnly(context);
        Assert.assertEquals("value", context.getAsString("key"));
    }

    @Test
    public void testExecuteOnlyEnd() throws Exception {
        final DefaultEndCommand<GenericParameterObject> processTestStartCommand = new DefaultEndCommand<GenericParameterObject>();
        GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        processTestStartCommand.executeOnly(context);
        Assert.assertEquals("value", context.getAsString("key"));
    }
    

    @Test
    public void getProcessNameTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        final String result = processTestStartCommand.getProcessID();
        assertEquals("Start", result);
    }

    @Test
    public void processANullContainer() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final DefaultCommandContainer<GenericParameterObject> container = new DefaultCommandContainer<GenericParameterObject>();
        String result = container.executeAsProcess("Start", context);
        assertNull(result);
        result = container.getProcessID();
        assertNull(result);
    }

    @Test
    public void testDefaultMethods() throws Exception {
        final ProcessCommand<ParameterObject> pc = new ProcessCommand<ParameterObject>() {

            @Override
            public void execute(final ParameterObject context) throws CommandException {
            }

            @Override
            public String executeAsProcess(final String startCommand, final ParameterObject context) {
                return "executeAsProcess";
            }

            @Override
            public String getProcessID() {
                return "getProcessID";
            }

            @Override
            public void setProcessID(final String processID) {
            }

            @Override
            public void executeOnly(ParameterObject context) {
            }

        };
        assertThat(pc.executeAsProcess("", DefaultParameterObject.NULLCONTEXT), is("executeAsProcess"));
        assertThat(pc.getProcessID(), is("getProcessID"));
        assertThat(pc.getTransitionList(), notNullValue());
        assertThat(pc.findNext("Hello"), is("Hello"));
        final Transition transition = new DefaultTransition();
        pc.addTransition(transition);
    }
}
