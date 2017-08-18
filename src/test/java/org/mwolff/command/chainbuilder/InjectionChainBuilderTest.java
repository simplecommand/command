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
package org.mwolff.command.chainbuilder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.commons.command.DefaultParameterObject;
import org.mwolff.commons.command.DefaultTransition;
import org.mwolff.commons.command.GenericParameterObject;
import org.mwolff.commons.command.iface.Command;
import org.mwolff.commons.command.iface.Transition;
import org.mwolff.commons.command.samplecommands.ExceptionCommand;
import org.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import org.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import org.mwolff.commons.command.samplecommands.ProcessTestCommandEnd;
import org.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class InjectionChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExecuteOnly() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        builder.executeOnly(context);
        Assert.assertEquals("value", context.getAsString("key"));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final GenericParameterObject context = new DefaultParameterObject();
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<>(
                "Start");
        Transition transition = new DefaultTransition();
        transition.setReturnValue("OK");
        transition.setTarget("Next");
        processTestStartCommand.addTransition(transition);
        transition = new DefaultTransition();
        transition.setReturnValue("NOK");
        transition.setTarget("Start");
        processTestStartCommand.addTransition(transition);

        final ProcessTestCommandEnd<GenericParameterObject> processTestEndCommand = new ProcessTestCommandEnd<>(
                "Next");

        commandList.add(processTestStartCommand);
        commandList.add(processTestEndCommand);
        builder.setCommands(commandList);
        builder.executeAsProcess("Start", context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - Next - ", processflow);
        Assert.assertNull(builder.getProcessID());
    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final GenericParameterObject context = new DefaultParameterObject();
        Command<GenericParameterObject> command = new PriorityOneTestCommand<>();
        commandList.add(command);
        command = new PriorityTwoTestCommand<>();
        commandList.add(command);
        builder.setCommands(commandList);
        builder.execute(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    @Test
    public void testsetProcessID() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ProcessID cannot be set on Container.");
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        builder.setProcessID("something");
    }

    @Test
    public void testSpringChainBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new ExceptionCommand<>();
        commandList.add(command);
        builder.setCommands(commandList);
        final boolean result = builder.executeAsChain(context);
        Assert.assertFalse(result);
    }
}