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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.mwolff.commons.command.DefaultContext;
import de.mwolff.commons.command.GenericContext;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.samplecommands.ExceptionCommand;
import de.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandEnd;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class InjectionChainBuilderTest {

    @Test
    public void testSpringChainBuilder() throws Exception {
        final InjectionChainBuilder<GenericContext> builder = new InjectionChainBuilder<GenericContext>();
        final List<Command<GenericContext>> commandList = new ArrayList<Command<GenericContext>>();
        final GenericContext context = new DefaultContext();
        final Command<GenericContext> command = new ExceptionCommand<GenericContext>();
        commandList.add(command);
        builder.setCommands(commandList);
        final boolean result = builder.executeAsChain(context);
        Assert.assertFalse(result);
    }
    
    @Test
	public void testExecuteMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericContext> builder = new InjectionChainBuilder<GenericContext>();
        final List<Command<GenericContext>> commandList = new ArrayList<Command<GenericContext>>();
        final GenericContext context = new DefaultContext();
        Command<GenericContext> command = new PriorityOneTestCommand<GenericContext>();
        commandList.add(command);
        command = new PriorityTwoTestCommand<GenericContext>();
        commandList.add(command);
        builder.setCommands(commandList);
        builder.execute(context);
        assertEquals("1-2-", context.getAsString("priority"));
	}
    
    @Test
	public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final InjectionChainBuilder<GenericContext> builder = new InjectionChainBuilder<GenericContext>();
        final List<Command<GenericContext>> commandList = new ArrayList<Command<GenericContext>>();
		GenericContext context = new DefaultContext();
		ProcessTestCommandStart<GenericContext> processTestStartCommand = new ProcessTestCommandStart<GenericContext>("Start");
		ProcessTestCommandEnd<GenericContext> processTestEndCommand = new ProcessTestCommandEnd<GenericContext>("Next");
		commandList.add(processTestStartCommand);
		commandList.add(processTestEndCommand);
        builder.setCommands(commandList);
		builder.executeAsProcess("Start", context);
		String processflow = context.getAsString("result");
		assertEquals("Start - Next - ", processflow);
		assertNull(builder.getProcessID());
		
	}

}