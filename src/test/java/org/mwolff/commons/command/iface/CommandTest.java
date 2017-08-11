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
package org.mwolff.commons.command.iface;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.commons.command.DefaultParameterObject;
import org.mwolff.commons.command.GenericParameterObject;
import org.mwolff.commons.command.samplecommands.ExceptionCommand;
import org.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import org.mwolff.commons.command.samplecommands.PriorityThreeTestCommand;
import org.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import org.mwolff.commons.command.samplecommands.SimpleTestCommand;
import org.mwolff.commons.command.samplecommands.VerySimpleTestCommand;

public class CommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCommandInterface() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new SimpleTestCommand<GenericParameterObject>();
        command.execute(context);
        Assert.assertEquals("SimpleTestCommand", context.getAsString("SimpleTestCommand"));
    }

    @Test
    public void testCommandWithResult() throws Exception {
        final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<GenericParameterObject>();
        final boolean result = command.executeAsChain(DefaultParameterObject.NULLCONTEXT);
        Assert.assertTrue(result);
    }

    @Test
    public void testDefaultBehaviorWithException() throws Exception {
        final ChainCommand<DefaultParameterObject> command = new ExceptionCommand<DefaultParameterObject>();
        final DefaultParameterObject context = new DefaultParameterObject();
        command.executeAsChain(context);
        final String result = context.getAsString("executed");
        Assert.assertEquals("true", result);
    }

    @Test
    public void testDefaultCommandAsChainFalse() throws Exception {
        final ExceptionCommand<GenericParameterObject> defaultCommand = new ExceptionCommand<GenericParameterObject>();
        Assert.assertFalse(defaultCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT));
    }

    @Test
    public void testException() throws Exception {
        final ChainCommand<DefaultParameterObject> command = new ExceptionCommand<DefaultParameterObject>();
        final DefaultParameterObject context = new DefaultParameterObject();
        Assert.assertFalse(command.executeAsChain(context));
    }

    @Test
    public void testPriorityCommands() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        Command<GenericParameterObject> command = new PriorityOneTestCommand<GenericParameterObject>();
        command.execute(context);
        Assert.assertEquals("PriorityOneTestCommand", context.getAsString("PriorityOneTestCommand"));
        command = new PriorityTwoTestCommand<GenericParameterObject>();
        command.execute(context);
        Assert.assertEquals("PriorityTwoTestCommand", context.getAsString("PriorityTwoTestCommand"));
        command = new PriorityThreeTestCommand<GenericParameterObject>();
        command.execute(context);
        Assert.assertEquals("PriorityThreeTestCommand", context.getAsString("PriorityThreeTestCommand"));
    }

    @Test
    public void testPriorThreeFirstCall() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new PriorityThreeTestCommand<GenericParameterObject>();
        command.execute(context);
        Assert.assertEquals("PriorityThreeTestCommand", context.getAsString("PriorityThreeTestCommand"));
    }

    @Test
    public void testPriorTwoFirstCall() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new PriorityTwoTestCommand<GenericParameterObject>();
        command.execute(context);
        Assert.assertEquals("PriorityTwoTestCommand", context.getAsString("PriorityTwoTestCommand"));
    }
    
    @Test
    public void testVerySimpleCommand() throws Exception {
        GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new VerySimpleTestCommand<>();
        context = command.executeOnly(context);
        Assert.assertEquals("newValue", context.getAsString("key"));
        
        
    }
    

}
