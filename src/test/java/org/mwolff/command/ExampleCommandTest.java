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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mwolff.command.chain.InjectionChainBuilder;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.samplecommands.PriorityOneTestCommand;
import org.mwolff.command.samplecommands.PriorityThreeTestCommand;
import org.mwolff.command.samplecommands.PriorityTwoTestCommand;
import org.mwolff.command.samplecommands.SimpleTestCommand;

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

        final List<Command<GenericParameterObject>> commands = new ArrayList<>();
        commands.add(new PriorityOneTestCommand<>());
        commands.add(new PriorityTwoTestCommand<>());
        final InjectionChainBuilder<GenericParameterObject> builder = new InjectionChainBuilder<>();

        // injection usually will be done by a injection container
        builder.setCommands(commands);
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        builder.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("1-2-", priorString);
    }

    /*
     * Chain example. You can execute commands as a chain. The execution is
     * stopped if one command returns false.
     */
    @Test
    public void testExecuteCommandsAsChain() throws Exception {

        final GenericParameterObject context = new DefaultParameterObject();
        final CommandContainer<GenericParameterObject> commandContainer = new DefaultCommandContainer<>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<>());

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<>();
        mixedList.addCommand(1, new SimpleTestCommand<>());
        mixedList.addCommand(2, commandContainer);

        mixedList.executeCommandAsChain(context);
        final String priorString = context.getAsString("priority");
        Assert.assertEquals("S-1-2-3-", priorString);
    }

    /*
     * Simple example. Put all commands in a container and execute it by
     * bypassing a context. All commands in the container will be executed in
     * the sequence they were inserted.
     */
    @Test
    public void testExecuteCommandsWithContext() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandContainer<GenericParameterObject> container = new DefaultCommandContainer<>();
        container.addCommand(new PriorityOneTestCommand<>());
        container.addCommand(new PriorityTwoTestCommand<>());
        container.executeCommand(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    /*
     * Priority example. Put all commands in a container by adding a priority.
     * All commands in the container will be executed in order of the priority.
     */
    @Test
    public void testExecuteCommandsWithContextAndPriority() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandContainer<GenericParameterObject> container = new DefaultCommandContainer<>();
        container.addCommand(3, new PriorityThreeTestCommand<>());
        container.addCommand(2, new PriorityOneTestCommand<>());
        container.addCommand(1, new PriorityTwoTestCommand<>());
        container.executeCommand(context);
        Assert.assertEquals("2-1-3-", context.getAsString("priority"));
    }

    /*
     * Composite example. You can add commands as well as command containers in
     * a simple container.
     */
    @Test
    public void testExecuteCommandsWithMixedContent() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final CommandContainer<GenericParameterObject> commandContainer = new DefaultCommandContainer<>();
        commandContainer.addCommand(1, new PriorityOneTestCommand<>());
        commandContainer.addCommand(2, new PriorityTwoTestCommand<>());
        commandContainer.addCommand(3, new PriorityThreeTestCommand<>());

        final CommandContainer<GenericParameterObject> mixedList = new DefaultCommandContainer<>();
        context.put("priority", "");
        mixedList.addCommand(new SimpleTestCommand<>());
        mixedList.addCommand(commandContainer);

        mixedList.executeCommand(context);
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
        final CommandContainer<GenericParameterObject> container = new DefaultCommandContainer<>();
        container.addCommand(new PriorityOneTestCommand<>());
        container.addCommand(new PriorityTwoTestCommand<>());
        container.executeCommand(DefaultParameterObject.NULLCONTEXT);
    }
}
