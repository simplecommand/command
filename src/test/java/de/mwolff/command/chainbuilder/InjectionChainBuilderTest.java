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
package de.mwolff.command.chainbuilder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.DefaultParameterObject;
import de.mwolff.commons.command.DefaultTransition;
import de.mwolff.commons.command.GenericParameterObject;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.Transition;
import de.mwolff.commons.command.samplecommands.ExceptionCommand;
import de.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandEnd;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class InjectionChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSpringChainBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<GenericParameterObject>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<Command<GenericParameterObject>>();
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new ExceptionCommand<GenericParameterObject>();
        commandList.add(command);
        builder.setCommands(commandList);
        final boolean result = builder.executeAsChain(context);
        Assert.assertFalse(result);
    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<GenericParameterObject>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<Command<GenericParameterObject>>();
        final GenericParameterObject context = new DefaultParameterObject();
        Command<GenericParameterObject> command = new PriorityOneTestCommand<GenericParameterObject>();
        commandList.add(command);
        command = new PriorityTwoTestCommand<GenericParameterObject>();
        commandList.add(command);
        builder.setCommands(commandList);
        builder.execute(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<GenericParameterObject>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<Command<GenericParameterObject>>();
        final GenericParameterObject context = new DefaultParameterObject();
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<GenericParameterObject>(
                "Start");
        Transition transition = new DefaultTransition();
        transition.setReturnValue("OK");
        transition.setTarget("Next");
        processTestStartCommand.addTransition(transition);
        transition = new DefaultTransition();
        transition.setReturnValue("NOK");
        transition.setTarget("Start");
        processTestStartCommand.addTransition(transition);
        
        final ProcessTestCommandEnd<GenericParameterObject> processTestEndCommand = new ProcessTestCommandEnd<GenericParameterObject>(
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
    public void testsetProcessID() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ProcessID cannot be set on Container.");
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<GenericParameterObject>();
        builder.setProcessID("something");
    }
}