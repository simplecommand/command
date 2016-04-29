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
package de.mwolff.commons.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;
import de.mwolff.commons.command.iface.ProcessCommand;
import de.mwolff.commons.command.iface.Transition;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandEnd;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class ProcessCommandTest {

    @Test
    public void executeAsProcessSimpleTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        final GenericParameterObject context = new DefaultParameterObject();
        final String result = processTestStartCommand.executeAsProcess("", context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - ", processflow);
        Assert.assertEquals("OK", result);
    }

    @Test
    @Ignore
    public void executeTwoSimpleProcessesInARow() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        final ProcessTestCommandEnd<GenericParameterObject> processTestEndCommand = new ProcessTestCommandEnd<GenericParameterObject>(
                "Next");
        final DefaultCommandContainer<GenericParameterObject> container = new DefaultCommandContainer<GenericParameterObject>();
        container.addCommand(processTestStartCommand);
        container.addCommand(processTestEndCommand);
        container.executeAsProcess("Start", context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - Next - ", processflow);
    }

    @Test
    public void getProcessNameTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        final String result = processTestStartCommand.getProcessID();
        Assert.assertEquals("Start", result);
    }

    @Test
    public void processANullContainer() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final DefaultCommandContainer<GenericParameterObject> container = new DefaultCommandContainer<GenericParameterObject>();
        String result = container.executeAsProcess("Start", context);
        Assert.assertNull(result);
        result = container.getProcessID();
        Assert.assertNull(result);
    }

    @Test
    public void testDefaultMethods() throws Exception {
        final ProcessCommand<ParameterObject> pc = new ProcessCommand<ParameterObject>() {

            @Override
            public void execute(ParameterObject context) throws CommandException {
            }

            @Override
            public String executeAsProcess(String startCommand, ParameterObject context) {
                return "executeAsProcess";
            }

            @Override
            public String getProcessID() {
                return "getProcessID";
            }

            @Override
            public void setProcessID(String processID) {
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
