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

package org.mwolff.command;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mwolff.command.interfaces.Command;
import org.mwolff.command.interfaces.CommandContainer;
import org.mwolff.command.interfaces.CommandTransition;
import org.mwolff.command.interfaces.ProcessCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.DefaultEndCommand;
import org.mwolff.command.samplecommands.*;
import org.mwolff.command.testcommand.TestCommand;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mwolff.command.interfaces.CommandTransition.NEXT;
import static org.mwolff.command.interfaces.CommandTransition.SUCCESS;

public class DefaultCommandContainerTest {

    private GenericParameterObject                   context;
    private CommandContainer<GenericParameterObject> commandContainer;

    @BeforeEach
    public void setUp() {
        context = new DefaultParameterObject();
        commandContainer = new DefaultCommandContainer<GenericParameterObject>();
    }

    @Test
    @DisplayName("addCommand works proper.")
    void addCommand() {
        commandContainer.addCommand(new TestCommand("test", SUCCESS));
        GenericParameterObject context = DefaultParameterObject.getInstance();
        @SuppressWarnings("unchecked")
        Map<Integer, Command<GenericParameterObject>> commandMap = (Map<Integer, Command<GenericParameterObject>>) ReflectionTestUtils
                .getField(commandContainer, "commandList");
        assertThat(commandMap.size(), is(1));
        TestCommand command = (TestCommand) commandMap.values().iterator().next();
        assertThat(command, notNullValue());

        CommandTransition result = command.executeCommand(context);
        String strResult = context.getAsString("resultString");
        assertThat(strResult, is("test"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    @DisplayName("addCommand with priority works proper.")
    void addCommandWithPriority() {
        commandContainer.addCommand(20, new TestCommand("test", SUCCESS));
        GenericParameterObject context = DefaultParameterObject.getInstance();
        @SuppressWarnings("unchecked")
        Map<Integer, TestCommand> commandMap = (Map<Integer, TestCommand>) ReflectionTestUtils
                .getField(commandContainer, "commandList");
        assertThat(commandMap.size(), is(1));
        Integer res = commandMap.keySet().iterator().next();
        assertThat(res, is(20));
        CommandTransition result = null;
        for (final TestCommand command : commandMap.values()) {
            result = command.executeCommand(context);
            assertThat(command, notNullValue());
        }
        String strResult = context.getAsString("resultString");
        assertThat(strResult, is("test"));
        assertThat(result, is(SUCCESS));
    }

    /*
     * Creates some Commands in different Order. There are two commands with
     * prio 1!
     */
    private CommandContainer<GenericParameterObject> createCommandInOrderWithPrioritySUCCESSes() {
        commandContainer.addCommand(1, new TestCommand("1-", SUCCESS))
        .addCommand(3, new TestCommand("3-", SUCCESS))
        .addCommand(1, new TestCommand("2-", SUCCESS));
        return commandContainer;
    }

    /*
     * Creating three commands with prio 1-2-3 for execution
     */
    private CommandContainer<GenericParameterObject> createDefaultCommands() {
        commandContainer.addCommand(new TestCommand("1-", NEXT)).addCommand(new TestCommand("2-", NEXT))
                .addCommand(new TestCommand("3-", NEXT));
        return commandContainer;
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainerAndExecute() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrderWithPrioritySUCCESSes();
        context.put("resultString", "");
        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
    }

    @Test
    public void testExecuteCommandSuccess() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrderWithPrioritySUCCESSes();
        context.put("priority", "");
        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
    }

    @Test
    public void testExecuteCommandAsChainSuccessABORT() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrderWithPrioritySUCCESSes();
        context.put("priority", "");
        final CommandTransition transition = commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
        assertEquals(transition, CommandTransition.SUCCESS);

    }

    @Test
    public void testExecuteCommandAsChainSuccessFAIL() throws Exception {
        commandContainer.addCommand(new FailureTestCommand<>());
        context.put("priority", "");

        final CommandTransition transition = commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        assertEquals(priorString, "");
        assertEquals(transition, CommandTransition.FAILURE);

    }

    @Test
    public void testExecuteOnlyOnContainer() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrderWithPrioritySUCCESSes();
        context.put("priority", "");
        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainerAndExecuteAsChain() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrderWithPrioritySUCCESSes();
        context.put("priority", "");
        commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So
     * the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainerAndExecute() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createDefaultCommands();
        context.put("resultString", "");

        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So
     * the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainerAndExecuteAsChain() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createDefaultCommands();
        context.put("resultString", "");

        commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("resultString");
        assertEquals("1-2-3-", priorString);
    }

    @Test
    public void testChainWithFailure() throws Exception {
        commandContainer.addCommand(1, new ExceptionCommand<>());
        commandContainer.addCommand(2, new SimpleTestCommand<>());
        final CommandTransition transition = commandContainer.executeCommand(context);
        context.getAsString("priority");
        assertEquals(CommandTransition.FAILURE, transition);

    }

    @Test
    public void testEndCommand() throws Exception {
        final ProcessCommand<GenericParameterObject> search = new DefaultEndCommand();
        search.setProcessID("END");
        commandContainer.addCommand(search);
        final String result = commandContainer.executeAsProcess(context);
        assertEquals(null, result);
    }

    // Remark: Should work if no command is inserted
    @Test
    public void testExecuteWithNullCommands() throws Exception {
        final String result = commandContainer.executeAsProcess(null, context);
        assertEquals(null, result);

    }

    @Test
    public void testGetCommandWithProcessID() throws Exception {
        final Command<GenericParameterObject> search = new ProcessTestCommandStart<>("StartCommand");
        commandContainer.addCommand(1, search);
        commandContainer.addCommand(2, new ProcessTestCommandNext<>("NextCommand"));

        final Command<GenericParameterObject> found = commandContainer.getCommandByProcessID("StartCommand");
        assertSame(found, search);
    }

    /*
     * Remark: You can add either commands or command lists.
     */
    @Test
    public void testMixedModeInCommandContainer() throws Exception {
        commandContainer.addCommand(1, new TestCommand("1-", SUCCESS));
        commandContainer.addCommand(2, new TestCommand("2-", SUCCESS));
        commandContainer.addCommand(3, new TestCommand("3-", SUCCESS));

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<>();
        mixedList.addCommand(new SimpleTestCommand<>());
        mixedList.addCommand(commandContainer);

        mixedList.executeCommand(context);
        String priorString = context.getAsString("resultString");
        assertEquals("S-1-2-3-", priorString);
        mixedList.executeCommandAsChain(context);
        priorString = context.getAsString("resultString");
        assertEquals("S-1-2-3-S-1-2-3-", priorString);
    }

    @Test
    public void testsetProcessID() throws Exception {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            commandContainer.setProcessID("something");
        });
        assertThat(exception.getMessage(), CoreMatchers.is("ProcessID cannot be set on Container."));
    }
}
