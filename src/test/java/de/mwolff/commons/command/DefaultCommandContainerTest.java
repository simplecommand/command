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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.samplecommands.ExceptionCommand;
import de.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityThreeTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandNext;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandStart;
import de.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class DefaultCommandContainerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GenericParameterObject context;
    private CommandContainer<GenericParameterObject> commandContainer;

    @Before
    public void setUp() {
        context = new DefaultParameterObject();
        commandContainer = new DefaultCommandContainer<>();
    }

    /*
     * Creating three commands with prio 1-2-3 for execution
     */
    private CommandContainer<GenericParameterObject> createDefaultCommands() {
        commandContainer.addCommand(new PriorityOneTestCommand<GenericParameterObject>());
        commandContainer.addCommand(new PriorityTwoTestCommand<GenericParameterObject>());
        commandContainer.addCommand(new PriorityThreeTestCommand<GenericParameterObject>());
        return commandContainer;
    }

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainerAndExecute() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createDefaultCommands();
        commandContainer.execute(context);
        String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainerAndExecuteAsChain() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createDefaultCommands();
        commandContainer.executeAsChain(context);
        String priorString = context.getAsString("priority");
        Assert.assertEquals("A-B-C-", priorString);
    }

    /*
     * Creates some Commands in different Order. There are two commands with prio 1!
     */
    public CommandContainer<GenericParameterObject> createCommandInOrder() {
        commandContainer.addCommand(2, new PriorityThreeTestCommand<GenericParameterObject>());
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericParameterObject>());
        commandContainer.addCommand(1, new PriorityTwoTestCommand<GenericParameterObject>());
        return commandContainer;
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainerAndExecute() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        commandContainer.execute(context);
        String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-3-", priorString);
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainerAndExecuteAsChain() throws Exception {
        final CommandContainer<GenericParameterObject> commandContainer = createCommandInOrder();
        commandContainer.executeAsChain(context);
        String priorString = context.getAsString("priority");
        Assert.assertEquals("A-B-C-", priorString);
    }

    /*
     * Remark: You can add either commands or command lists.
     */
    @Test
    public void testMixedModeInCommandContainer() throws Exception {
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericParameterObject>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<GenericParameterObject>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<GenericParameterObject>());

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<>();
        mixedList.addCommand(new SimpleTestCommand<GenericParameterObject>());
        mixedList.addCommand(commandContainer);

        mixedList.execute(context);
        String priorString = context.getAsString("priority");
        Assert.assertEquals("S-1-2-3-", priorString);
        mixedList.executeAsChain(context);
        priorString = context.getAsString("priority");
        Assert.assertEquals("S-1-2-3-S-S-A-B-C-", priorString);
    }

    // Remark: Even ExceptionCommand throws an exception SimpleTextCommand is
    // executed
    @Test
    public void testChainWithError() throws Exception {
        commandContainer.addCommand(1, new ExceptionCommand<GenericParameterObject>());
        commandContainer.addCommand(2, new SimpleTestCommand<GenericParameterObject>());
        commandContainer.execute(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("S-", priorString);

    }

    // Remark: Should work if no command is inserted
    @Test
    public void testExecuteWithNullCommands() throws Exception {
        final String result = commandContainer.executeAsProcess(null, context);
        Assert.assertEquals(null, result);

    }

    @Test
    public void testsetProcessID() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ProcessID cannot be set on Container.");
        commandContainer.setProcessID("something");
    }

    @Test
    public void testGetCommandWithProcessID() throws Exception {
        Command<GenericParameterObject> search = new ProcessTestCommandStart<GenericParameterObject>("StartCommand");
        commandContainer.addCommand(1, search);
        commandContainer.addCommand(2, new ProcessTestCommandNext<GenericParameterObject>("NextCommand"));

        Command<GenericParameterObject> found = commandContainer.getCommandByProcessID("StartCommand");
        Assert.assertSame(found, search);
    }
}
