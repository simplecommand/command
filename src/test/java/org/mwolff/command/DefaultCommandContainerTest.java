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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransition.*;

import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.DefaultEndCommand;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.samplecommands.ExceptionCommand;
import org.mwolff.command.samplecommands.FailureTestCommand;
import org.mwolff.command.samplecommands.PriorityOneTestCommand;
import org.mwolff.command.samplecommands.PriorityThreeTestCommand;
import org.mwolff.command.samplecommands.PriorityTwoTestCommand;
import org.mwolff.command.samplecommands.ProcessTestCommandNext;
import org.mwolff.command.samplecommands.ProcessTestCommandStart;
import org.mwolff.command.samplecommands.SimpleTestCommand;
import org.mwolff.command.testcommand.TestCommand;
import org.springframework.test.util.ReflectionTestUtils;

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
        Map<Integer,Command<Object>> commandMap = (Map<Integer, Command<Object>>) ReflectionTestUtils.getField(commandContainer, "commandList");
        assertThat(commandMap.size(), is(1));
        CommandTransition result = commandContainer.executeCommand(context);
        String strResult = context.getAsString("resultString");
        assertThat(strResult, is("test"));
        assertThat(result, is(SUCCESS));
    }
    
    /*
     * Creates some Commands in different Order. There are two commands with
     * prio
     * 1!
     */
    public CommandContainer<GenericParameterObject> createCommandInOrder() {
        commandContainer.addCommand(2, new PriorityThreeTestCommand<>()).addCommand(1, new PriorityOneTestCommand<>())
                .addCommand(1, new PriorityTwoTestCommand<>());
        return commandContainer;
    }

    /*
     * Creating three commands with prio 1-2-3 for execution
     */
    private CommandContainer<GenericParameterObject> createDefaultCommands() {
        commandContainer.addCommand(new PriorityOneTestCommand<>()).addCommand(new PriorityTwoTestCommand<>())
                .addCommand(new PriorityThreeTestCommand<>());
        return commandContainer;
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainerAndExecute() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        context.put("priority", "");
        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    @Test
    public void testExecuteCommandSuccess() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        context.put("priority", "");
        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    @Test
    public void testExecuteCommandAsChainSuccessABORT() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        context.put("priority", "");
        final CommandTransition transition = commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
        Assert.assertEquals(transition, CommandTransition.NEXT);

    }

    @Test
    public void testExecuteCommandAsChainSuccessFAIL() throws Exception {
        commandContainer.addCommand(new FailureTestCommand<>());
        context.put("priority", "");

        final CommandTransition transition = commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals(priorString, "");
        Assert.assertEquals(transition, CommandTransition.FAILURE);

    }

    @Test
    public void testExecuteOnlyOnContainer() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        context.put("priority", "");
        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainerAndExecuteAsChain() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        context.put("priority", "");
        commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So
     * the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainerAndExecute() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createDefaultCommands();
        context.put("priority", "");

        commandContainer.executeCommand(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So
     * the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainerAndExecuteAsChain() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createDefaultCommands();
        context.put("priority", "");

        commandContainer.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    @Test
    public void testChainWithFailure() throws Exception {
        commandContainer.addCommand(1, new ExceptionCommand<>());
        commandContainer.addCommand(2, new SimpleTestCommand<>());
        final CommandTransition transition = commandContainer.executeCommand(context);
        context.getAsString("priority");
        Assert.assertEquals(CommandTransition.FAILURE, transition);

    }

    @Test
    public void testEndCommand() throws Exception {
        final ProcessCommand<GenericParameterObject> search = new DefaultEndCommand();
        search.setProcessID("END");
        commandContainer.addCommand(search);
        final String result = commandContainer.executeAsProcess(context);
        Assert.assertEquals(null, result);
    }

    // Remark: Should work if no command is inserted
    @Test
    public void testExecuteWithNullCommands() throws Exception {
        final String result = commandContainer.executeAsProcess(null, context);
        Assert.assertEquals(null, result);

    }

    @Test
    public void testGetCommandWithProcessID() throws Exception {
        final Command<GenericParameterObject> search = new ProcessTestCommandStart<>("StartCommand");
        commandContainer.addCommand(1, search);
        commandContainer.addCommand(2, new ProcessTestCommandNext<>("NextCommand"));

        final Command<GenericParameterObject> found = commandContainer.getCommandByProcessID("StartCommand");
        Assert.assertSame(found, search);
    }

    /*
     * Remark: You can add either commands or command lists.
     */
    @Test
    public void testMixedModeInCommandContainer() throws Exception {
        commandContainer.addCommand(1, new PriorityOneTestCommand<>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<>());

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<>();
        mixedList.addCommand(new SimpleTestCommand<>());
        mixedList.addCommand(commandContainer);

        mixedList.executeCommand(context);
        String priorString = context.getAsString("priority");
        Assert.assertEquals("S-1-2-3-", priorString);
        mixedList.executeCommandAsChain(context);
        priorString = context.getAsString("priority");
        Assert.assertEquals("S-1-2-3-S-1-2-3-", priorString);
    }

    @Test
    public void testsetProcessID() throws Exception {
        final Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            commandContainer.setProcessID("something");
        });
        Assert.assertThat(exception.getMessage(), CoreMatchers.is("ProcessID cannot be set on Container."));
    }
}
