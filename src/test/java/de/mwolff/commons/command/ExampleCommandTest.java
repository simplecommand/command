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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.mwolff.command.chainbuilder.InjectionChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.samplecommands.PriorityOneTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityThreeTestCommand;
import de.mwolff.commons.command.samplecommands.PriorityTwoTestCommand;
import de.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class ExampleCommandTest {

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

        final List<Command<GenericParameterObject>> commands = new ArrayList<Command<GenericParameterObject>>();
        commands.add(new PriorityOneTestCommand<GenericParameterObject>());
        commands.add(new PriorityTwoTestCommand<GenericParameterObject>());
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<GenericParameterObject>();
        // injection usually will be done by a injection container
        builder.setCommands(commands);
        final GenericParameterObject context = new DefaultParameterObject();
        builder.executeAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("A-B-", priorString);
    }

    /*
     * Chain example. You can execute commands as a chain. The execution is
     * stopped if one command returns false.
     */
    @Test
    public void testExecuteCommandsAsChain() throws Exception {

        final GenericParameterObject context = new DefaultParameterObject();
        final CommandContainer<GenericParameterObject> commandContainer = new DefaultCommandContainer<GenericParameterObject>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericParameterObject>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<GenericParameterObject>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<GenericParameterObject>());

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<GenericParameterObject>();
        mixedList.addCommand(1, new SimpleTestCommand<GenericParameterObject>());
        mixedList.addCommand(2, commandContainer);

        mixedList.executeAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("S-S-A-B-C-", priorString);
    }

    /*
     * Simple example. Put all commands in a container and execute it by
     * bypassing a context. All commands in the container will be executed in
     * the sequence they were inserted.
     */
    @Test
    public void testExecuteCommandsWithContext() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final CommandContainer<GenericParameterObject> container = new DefaultCommandContainer<GenericParameterObject>();
        container.addCommand(new PriorityOneTestCommand<GenericParameterObject>());
        container.addCommand(new PriorityTwoTestCommand<GenericParameterObject>());
        container.execute(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    /*
     * Priority example. Put all commands in a container by adding a priority.
     * All commands in the container will be executed in order of the priority.
     */
    @Test
    public void testExecuteCommandsWithContextAndPriority() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final CommandContainer<GenericParameterObject> container = new DefaultCommandContainer<GenericParameterObject>();
        container.addCommand(3, new PriorityThreeTestCommand<GenericParameterObject>());
        container.addCommand(2, new PriorityOneTestCommand<GenericParameterObject>());
        container.addCommand(1, new PriorityTwoTestCommand<GenericParameterObject>());
        container.execute(context);
        Assert.assertEquals("2-1-3-", context.getAsString("priority"));
    }

    /*
     * Composite example. You can add commands as well as command containers in
     * a simple container.
     */
    @Test
    public void testExecuteCommandsWithMixedContent() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final CommandContainer<GenericParameterObject> commandContainer = new DefaultCommandContainer<GenericParameterObject>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<GenericParameterObject>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<GenericParameterObject>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<GenericParameterObject>());

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<GenericParameterObject>();
        mixedList.addCommand(new SimpleTestCommand<GenericParameterObject>());
        mixedList.addCommand(commandContainer);

        mixedList.execute(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("S-1-2-3-", priorString);
    }

    /*
     * Simplest example. Put all commands in a container and execute it. All
     * commands in the container will be executed in the sequence they were
     * inserted.
     */
    @Test
    public void testExecuteCommandsWithoutContext() throws Exception {
        final CommandContainer<GenericParameterObject> container = new DefaultCommandContainer<GenericParameterObject>();
        container.addCommand(new PriorityOneTestCommand<GenericParameterObject>());
        container.addCommand(new PriorityTwoTestCommand<GenericParameterObject>());
        container.execute(DefaultParameterObject.NULLCONTEXT);
    }
}
