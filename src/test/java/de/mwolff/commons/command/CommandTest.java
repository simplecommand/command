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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandTest {

    @Test
    public void testCommandInterface() throws Exception {
        final GenericContext context = new DefaultContext();
        final Command<GenericContext> command = new SimpleTestCommand<GenericContext>();
        command.execute(context);
        assertEquals("SimpleTestCommand", context.getAsString("SimpleTestCommand"));
    }

    @Test
    public void testPriorityCommands() throws Exception {
        final GenericContext context = new DefaultContext();
        Command<GenericContext> command = new PriorityOneTestCommand<GenericContext>();
        command.execute(context);
        assertEquals("PriorityOneTestCommand", context.getAsString("PriorityOneTestCommand"));
        command = new PriorityTwoTestCommand<GenericContext>();
        command.execute(context);
        assertEquals("PriorityTwoTestCommand", context.getAsString("PriorityTwoTestCommand"));
        command = new PriorityThreeTestCommand<GenericContext>();
        command.execute(context);
        assertEquals("PriorityThreeTestCommand", context.getAsString("PriorityThreeTestCommand"));
    }

    @Test
    public void testPriorTwoFirstCall() throws Exception {
        final GenericContext context = new DefaultContext();
        final Command<GenericContext> command = new PriorityTwoTestCommand<GenericContext>();
        command.execute(context);
        assertEquals("PriorityTwoTestCommand", context.getAsString("PriorityTwoTestCommand"));
    }

    @Test
    public void testPriorThreeFirstCall() throws Exception {
        final GenericContext context = new DefaultContext();
        final Command<GenericContext> command = new PriorityThreeTestCommand<GenericContext>();
        command.execute(context);
        assertEquals("PriorityThreeTestCommand", context.getAsString("PriorityThreeTestCommand"));
    }

    @Test
    public void testCommandWithResult() throws Exception {
        final Command<GenericContext> command = new SimpleTestCommand<GenericContext>();
        final boolean result = command.executeAsChain(DefaultContext.NULLCONTEXT);
        assertTrue(result);
    }

    @Test
    public void testException() throws Exception {
        final Command<DefaultContext> command = new ExceptionCommand<DefaultContext>();
        final DefaultContext context = new DefaultContext();
        assertFalse(command.executeAsChain(context));
    }

    @Test
    public void testDefaultBehaviorWithException() throws Exception {
        final Command<DefaultContext> command = new ExceptionCommand<DefaultContext>();
        final DefaultContext context = new DefaultContext();
        command.executeAsChain(context);
        final String result = context.getAsString("executed");
        assertEquals("true", result);
    }

    @Test
    public void testDefaultCommandAsChainFalse() throws Exception {
        final ExceptionCommand<GenericContext> defaultCommand = new ExceptionCommand<GenericContext>();
        assertFalse(defaultCommand.executeAsChain(DefaultContext.NULLCONTEXT));
    }

    @Test
    public void testDefaultCommandAsChainTrue() throws Exception {
        final DefaultCommand<GenericContext> defaultCommand = new DefaultCommand<GenericContext>();
        assertTrue(defaultCommand.executeAsChain(DefaultContext.NULLCONTEXT));
    }
}
