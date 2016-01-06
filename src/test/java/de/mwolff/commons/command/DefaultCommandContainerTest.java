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

import static org.junit.Assert.*;

import org.junit.Test;

import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.samplecommands.ExceptionCommand;
import de.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityThreeTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import de.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class DefaultCommandContainerTest {

    /*
     * Remark: Adding commands without priority will mark all with priority 0.
     * So the execution is in natural order.
     */
    @Test
    public void testAddNoPriorityInCommandContainer() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        commandContainer.addCommand(new PriorityOneTestCommand<GenericContext>());
        commandContainer.addCommand(new PriorityTwoTestCommand<GenericContext>());
        commandContainer.addCommand(new PriorityThreeTestCommand<GenericContext>());
        commandContainer.execute(context);
        String priorString = context.getAsString("priority");
        assertEquals("1-2-3-", priorString);
        commandContainer.executeAsChain(context);
        priorString = context.getAsString("priority");
        assertEquals("1-2-3-A-B-C-", priorString);
    }

    /*
     * Remark: If there are two commands with the same priority, the first
     * inserted Command wins ... etc.
     */
    @Test
    public void testAddCommandWithPriorityInCommandContainer() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        commandContainer.addCommand(2, new PriorityThreeTestCommand<GenericContext>());
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericContext>());
        commandContainer.addCommand(1, new PriorityTwoTestCommand<GenericContext>());
        commandContainer.execute(context);
        String priorString = context.getAsString("priority");
        assertEquals("1-2-3-", priorString);
        commandContainer.executeAsChain(context);
        priorString = context.getAsString("priority");
        assertEquals("1-2-3-A-B-C-", priorString);
    }

    /*
     * Remark: You can add either commands or command lists.
     */
    @Test
    public void testMixedModeInCommandContainer() throws Exception {

        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericContext>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<GenericContext>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<GenericContext>());

        final CommandContainer<GenericContext> mixedList = new DefaultCommandContainer<GenericContext>();
        mixedList.addCommand(new SimpleTestCommand<GenericContext>());
        mixedList.addCommand(commandContainer);

        mixedList.execute(context);
        String priorString = context.getAsString("priority");
        assertEquals("S-1-2-3-", priorString);
        mixedList.executeAsChain(context);
        priorString = context.getAsString("priority");
        assertEquals("S-1-2-3-S-S-A-B-C-", priorString);
    }
    
    // Remark: Even ExceptionCommand throws an exception SimpleTextCommand is executed
    @Test
    public void testChainWithError() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        commandContainer.addCommand(1, new ExceptionCommand<GenericContext>());
        commandContainer.addCommand(2, new SimpleTestCommand<GenericContext>());
        commandContainer.execute(context);
        String priorString = context.getAsString("priority");
        assertEquals("S-", priorString);

    }
    
    // Remark: Should work if no command is inserted
    @Test
    public void testExecuteWithNullCommands() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        String result = commandContainer.executeAsProcess(null, context);
        assertEquals(null, result);
        
    }
}
