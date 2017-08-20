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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.commons.command.iface.CommandException;
import org.mwolff.commons.command.iface.ParameterObject;
import org.mwolff.commons.command.iface.ProcessCommand;
import org.mwolff.commons.command.iface.Transition;
import org.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class ProcessCommandTest {

    @Test
    public void executeAsProcessSimpleTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<>(
                "Start");
        final GenericParameterObject context = new DefaultParameterObject();
        final String result = processTestStartCommand.executeAsProcess(context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - ", processflow);
        Assert.assertEquals("OK", result);
    }

    @Test
    public void testExecuteOnlyStart() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<>(
                "Start");
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        processTestStartCommand.executeOnly(context);
        Assert.assertEquals("value", context.getAsString("key"));
    }

    @Test
    public void testExecuteOnlyEnd() throws Exception {
        final DefaultEndCommand<GenericParameterObject> processTestStartCommand = new DefaultEndCommand<>();
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        processTestStartCommand.executeOnly(context);
        Assert.assertEquals("value", context.getAsString("key"));
    }

    @Test
    public void getProcessNameTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<>(
                "Start");
        final String result = processTestStartCommand.getProcessID();
        Assert.assertEquals("Start", result);
    }

    @Test
    public void processANullContainer() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final DefaultCommandContainer<GenericParameterObject> container = new DefaultCommandContainer<>();
        String result = container.executeAsProcess("Start", context);
        Assert.assertNull(result);
        result = container.getProcessID();
        Assert.assertNull(result);
    }

    @Test
    public void testDefaultMethods() throws Exception {
        final ProcessCommand<ParameterObject> pc = new ProcessCommand<ParameterObject>() {

            @Override
            public void execute(final ParameterObject context) throws CommandException {
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

            @Override
            public boolean executeAsChain(ParameterObject parameterObject) {
                return false;
            }

        };
        Assert.assertNull(pc.executeAsProcess("START", DefaultParameterObject.NULLCONTEXT));
        Assert.assertNull(pc.executeAsProcess(DefaultParameterObject.NULLCONTEXT));
        Assert.assertThat(pc.getProcessID(), CoreMatchers.is("getProcessID"));
        Assert.assertThat(pc.getTransitionList(), CoreMatchers.notNullValue());
        Assert.assertThat(pc.findNext("Hello"), CoreMatchers.is("Hello"));
        final Transition transition = new DefaultTransition();
        pc.addTransition(transition);
    }
}
