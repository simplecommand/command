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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.mwolff.command.chainbuilder.InjectionChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityThreeTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import de.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class ExampleCommandTest {

    /*
     * Simplest example. Put all commands in a container and execute it. All
     * commands in the container will be executed in the sequence they were
     * inserted.
     */
    @Test
    public void testExecuteCommandsWithoutContext() throws Exception {
        final CommandContainer<GenericContext> container = new DefaultCommandContainer<GenericContext>();
        container.addCommand(new PriorityOneTestCommand<GenericContext>());
        container.addCommand(new PriorityTwoTestCommand<GenericContext>());
        container.execute(DefaultContext.NULLCONTEXT);
    }

    /*
     * Simple example. Put all commands in a container and execute it by
     * bypassing a context. All commands in the container will be executed in
     * the sequence they were inserted.
     */
    @Test
    public void testExecuteCommandsWithContext() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> container = new DefaultCommandContainer<GenericContext>();
        container.addCommand(new PriorityOneTestCommand<GenericContext>());
        container.addCommand(new PriorityTwoTestCommand<GenericContext>());
        container.execute(context);
        assertEquals("1-2-", context.getAsString("priority"));
    }

    /*
     * Priority example. Put all commands in a container by adding a priority.
     * All commands in the container will be executed in order of the priority.
     */
    @Test
    public void testExecuteCommandsWithContextAndPriority() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> container = new DefaultCommandContainer<GenericContext>();
        container.addCommand(3, new PriorityThreeTestCommand<GenericContext>());
        container.addCommand(2, new PriorityOneTestCommand<GenericContext>());
        container.addCommand(1, new PriorityTwoTestCommand<GenericContext>());
        container.execute(context);
        assertEquals("2-1-3-", context.getAsString("priority"));
    }

    /*
     * Composite example. You can add commands as well as command containers in
     * a simple container.
     */
    @Test
    public void testExecuteCommandsWithMixedContent() throws Exception {
        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericContext>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<GenericContext>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<GenericContext>());

        final CommandContainer<GenericContext> mixedList = new DefaultCommandContainer<GenericContext>();
        mixedList.addCommand(new SimpleTestCommand<GenericContext>());
        mixedList.addCommand(commandContainer);

        mixedList.execute(context);
        final String priorString = context.getAsString("priority");
        assertEquals("S-1-2-3-", priorString);
    }

    /*
     * Chain example. You can execute commands as a chain. The execution is
     * stopped if one command returns false.
     */
    @Test
    public void testExecuteCommandsAsChain() throws Exception {

        final GenericContext context = new DefaultContext();
        final CommandContainer<GenericContext> commandContainer = new DefaultCommandContainer<GenericContext>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericContext>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<GenericContext>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<GenericContext>());

        final CommandContainer<GenericContext> mixedList = new DefaultCommandContainer<GenericContext>();
        mixedList.addCommand(1, new SimpleTestCommand<GenericContext>());
        mixedList.addCommand(2, commandContainer);

        mixedList.executeAsChain(context);
        final String priorString = context.getAsString("priority");
        assertEquals("S-A-B-C-", priorString);
    }

    /**
     * You can use the builder to build and execute the chain. Usually you will
     * use the builder together with a dependency framework just as Spring. In
     * this case Spring instantiates the builder as well as all commands and
     * injects all commands as a list.
     *
     * &lt;bean id="firstCommand"
     * class="de.mwolff.commons.command.PriorityOneCommand"&gt; &lt;/bean&gt;
     *
     * &lt;bean id="secondCommand"
     * class="de.mwolff.commons.command.PriorityTwoCommand"&gt; &lt;/bean&gt;
     *
     * &lt;bean id="chainBuilder"
     * class="de.mwolff.command.chainbuilder.InjectionChainBuilder"&gt;
     * &lt;property name="commands"&gt; &lt;list&gt; &lt;ref bean="firstCommand"
     * /&gt; &lt;ref bean="secondCommand" /&gt; &lt;/list&gt; &lt;/property&gt;
     * &lt;/bean&gt;
     */
    @Test
    public void testBuilderExample() throws Exception {

        final List<Command<GenericContext>> commands = new ArrayList<Command<GenericContext>>();
        commands.add(new PriorityOneTestCommand<GenericContext>());
        commands.add(new PriorityTwoTestCommand<GenericContext>());
        final InjectionChainBuilder<GenericContext> builder = new InjectionChainBuilder<GenericContext>();
        // injection usually will be done by a injection container
        builder.setCommands(commands);
        final GenericContext context = new DefaultContext();
        builder.executeAsChain(context);
        final String priorString = context.getAsString("priority");
        assertEquals("A-B-", priorString);
    }
}
