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

package org.mwolff.command.builder;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mwolff.command.interfaces.Command;
import org.mwolff.command.interfaces.CommandTransition;
import org.mwolff.command.interfaces.Transition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.DefaultTransition;
import org.mwolff.command.samplecommands.*;
import org.mwolff.command.testcommand.TestCommand;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InjectionChainBuilderTest {

    @Test
    public void testExecuteOnly() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        builder.executeCommand(context);
        assertEquals("value", context.getAsString("key"));
    }

    @Test
    public void testExecuteFailureChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final FailureTestCommand<GenericParameterObject> failureTestCommand = new FailureTestCommand<>();
        commandList.add(failureTestCommand);
        builder.setCommands(commandList);
        final CommandTransition result = builder.executeCommandAsChain(context);
        assertEquals(CommandTransition.FAILURE, result);
    }

    @Test
    public void testExecuteAbortChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final DoneTestCommand<GenericParameterObject> failureTestCommand = new DoneTestCommand<>();
        commandList.add(failureTestCommand);
        builder.setCommands(commandList);
        final CommandTransition result = builder.executeCommandAsChain(context);
        assertEquals(CommandTransition.DONE, result);
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

        final ProcessTestCommandEnd<GenericParameterObject> processTestEndCommand = new ProcessTestCommandEnd<>("Next");

        commandList.add(processTestStartCommand);
        commandList.add(processTestEndCommand);
        builder.setCommands(commandList);
        builder.executeAsProcess("Start", context);
        final String processflow = context.getAsString("result");
        assertEquals("Start - Next - ", processflow);
        assertNull(builder.getProcessID());
    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final GenericParameterObject context = new DefaultParameterObject();
        Command<GenericParameterObject> command = new TestCommand("1-", CommandTransition.NEXT);
        context.put("resultString", "");
        commandList.add(command);
        command = new TestCommand("2-", CommandTransition.NEXT);
        commandList.add(command);
        builder.setCommands(commandList);
        builder.executeCommand(context);
        assertEquals("1-2-", context.getAsString("resultString"));
    }

    @Test
    public void testsetProcessID() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final Throwable exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            builder.setProcessID("something");
        });
        assertThat(exception.getMessage(), CoreMatchers.is("ProcessID cannot be set on Container."));
    }

    @Test
    public void testExecuteAsProcess() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final Throwable exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            builder.executeAsProcess(new DefaultParameterObject());
        });
        assertThat(exception.getMessage(), CoreMatchers.is("Use executeAsProcess(String start, T context"));
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final CommandTransition result = builder.executeCommand(new DefaultParameterObject());
        assertEquals(result, CommandTransition.SUCCESS);
    }

    @Test
    public void testSpringCommandChainBuilder() throws Exception {
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();
        final List<Command<GenericParameterObject>> commandList = new ArrayList<>();
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new ExceptionCommand<>();
        commandList.add(command);
        builder.setCommands(commandList);
        final CommandTransition result = builder.executeCommandAsChain(context);
        assertEquals(result, CommandTransition.DONE);
    }
}