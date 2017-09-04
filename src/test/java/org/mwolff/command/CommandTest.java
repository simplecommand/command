/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>
    
    Download: https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git

    Copyright (C) 2018 Manfred Wolff and the simple command community

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

package org.mwolff.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.samplecommands.ExceptionCommand;
import org.mwolff.command.samplecommands.PriorityOneTestCommand;
import org.mwolff.command.samplecommands.PriorityThreeTestCommand;
import org.mwolff.command.samplecommands.PriorityTwoTestCommand;
import org.mwolff.command.samplecommands.SimpleTestCommand;

public class CommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInterfaceDefaultExecuteCommand() {
        
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new Command<GenericParameterObject>() {

            @Override
            public void execute(GenericParameterObject parameterObject) throws CommandException {
                throw new UnsupportedOperationException("Use executeCommand() instead");
            }
            
            @Override
            public CommandTransition executeCommand(GenericParameterObject parameterObject) {
                return Command.super.executeCommand(parameterObject);
            }

        };
        
        CommandTransition transition = command.executeCommand(context);
        assertThat(transition, is(CommandTransition.SUCCESS));
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testInterfaceDefaultExecute() throws Exception {
        
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new Command<GenericParameterObject>() {

            @Override
            public void execute(GenericParameterObject parameterObject) throws CommandException {
                throw new UnsupportedOperationException("Use executeCommand() instead");
            }
            
            @Override
            public CommandTransition executeCommand(GenericParameterObject parameterObject) {
                return Command.super.executeCommand(parameterObject);
            }

        };
        
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Use executeCommand() instead");
        command.execute(context);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInterfaceDefaultExecuteWithNoContext() throws Exception {
        
        final Command<GenericParameterObject> command = new Command<GenericParameterObject>() {

            @Override
            public void execute(GenericParameterObject parameterObject) throws CommandException {
                throw new UnsupportedOperationException("Use executeCommand() instead");
            }
            
            @Override
            public CommandTransition executeCommand(GenericParameterObject parameterObject) {
                return Command.super.executeCommand(parameterObject);
            }

        };
        
        CommandTransition transition = command.executeCommand(null);
        assertThat(transition, is(CommandTransition.FAILURE));
    }
 

    @SuppressWarnings("deprecation")
    @Test
    public void testDefaultBehaviorWithException() throws Exception {
        final ChainCommand<DefaultParameterObject> command = new ExceptionCommand<>();
        final DefaultParameterObject context = new DefaultParameterObject();
        command.executeAsChain(context);
        final String result = context.getAsString("executed");
        Assert.assertEquals("true", result);
    }

    @Test
    public void testDefaultCommandAsChainFalse() throws Exception {
        final ExceptionCommand<GenericParameterObject> defaultCommand = new ExceptionCommand<>();
        Assert.assertFalse(defaultCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testException() throws Exception {
        final ChainCommand<DefaultParameterObject> command = new ExceptionCommand<>();
        final DefaultParameterObject context = new DefaultParameterObject();
        Assert.assertFalse(command.executeAsChain(context));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPriorityCommands() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        Command<GenericParameterObject> command = new PriorityOneTestCommand<>();
        command.execute(context);
        Assert.assertEquals("PriorityOneTestCommand", context.getAsString("PriorityOneTestCommand"));
        command = new PriorityTwoTestCommand<>();
        command.execute(context);
        Assert.assertEquals("PriorityTwoTestCommand", context.getAsString("PriorityTwoTestCommand"));
        command = new PriorityThreeTestCommand<>();
        command.execute(context);
        Assert.assertEquals("PriorityThreeTestCommand", context.getAsString("PriorityThreeTestCommand"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPriorThreeFirstCall() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new PriorityThreeTestCommand<>();
        command.execute(context);
        Assert.assertEquals("PriorityThreeTestCommand", context.getAsString("PriorityThreeTestCommand"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testPriorTwoFirstCall() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new PriorityTwoTestCommand<>();
        command.execute(context);
        Assert.assertEquals("PriorityTwoTestCommand", context.getAsString("PriorityTwoTestCommand"));
    }


}
