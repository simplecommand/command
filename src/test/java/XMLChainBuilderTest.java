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

package org.mwolff.command.chain;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.Command;
import org.mwolff.command.CommandContainer;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.process.Transition;
import org.springframework.test.util.ReflectionTestUtils;

public class XMLChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExecuteOnly() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        context.put("key", "value");
        final XMLChainBuilder<GenericParameterObject> builder = new XMLChainBuilder<>("/invalidXMLDocument.xml");
        builder.execute(context);
        Assert.assertEquals("value", context.getAsString("key"));
    }

    @Test
    public void chainbuilderExists() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("");
        Assert.assertThat(xmlChainBuilder, CoreMatchers.instanceOf(XMLChainBuilder.class));
    }

    @Test
    public void createInvalidXMLDocument() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("XML Document could not created");
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/invalidXMLDocument.xml");
        xmlChainBuilder.buildChain();
    }

    @Test
    public void emptyCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainEmpty.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Object>> commands = (List<Command<Object>>) ReflectionTestUtils.getField(xmlChainBuilder,
                "actions");
        Assert.assertThat(returnvalue, Matchers.is(true));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(true));
    }

    @Test
    public void executeWithException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainOneComandException.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.execute(context);
        Assert.assertEquals("true", context.getAsString("executed"));
    }

    @Test
    public void invalidCommandInserted() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/invalidCommandChain.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean result = xmlChainBuilder.executeAsChain(context);
        Assert.assertThat(result, Matchers.is(false));
    }

    @Test
    public void loadInvalidXMLFileFromInputStream() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Could not read xml file");
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/notExists.xml");
        xmlChainBuilder.buildChain();
    }

    @Test
    public void TestSetProcessIdOnContainer() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ProcessID cannot be set on Container.");
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/notExists.xml");
        xmlChainBuilder.setProcessID("HelloWorld");
    }

    @Test
    public void oneCommandInserted() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainOneComandSimple.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Object>> commands = (List<Command<Object>>) ReflectionTestUtils.getField(xmlChainBuilder,
                "actions");
        Assert.assertThat(returnvalue, Matchers.is(true));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(false));
        Assert.assertThat(commands.size(), Matchers.is(1));
    }

    @Test
    public void oneExceptionCommandInserted() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainOneComandException.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Object>> commands = (List<Command<Object>>) ReflectionTestUtils.getField(xmlChainBuilder,
                "actions");
        Assert.assertThat(returnvalue, Matchers.is(false));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(false));
        Assert.assertThat(commands.size(), Matchers.is(1));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainProcess.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - Next - Start - Next - ", processflow);
    }

    @Test
    public void testExecuteAsProcessMethodForBuilderWIthException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("notExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.execute(context);
        Assert.assertNull(xmlChainBuilder.getProcessID());
    }

    @Test
    public void testExecuteAsProcessWithException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainProcessNotExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);

    }

    @Test
    public void testExecuteAsProcess() throws Exception {
        final XMLChainBuilder<GenericParameterObject> builder = new XMLChainBuilder<>("/commandChainProcess.xml");
        final String result = builder.executeAsProcess(DefaultParameterObject.NULLCONTEXT);
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        xmlChainBuilder.execute(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommand(context);
        Assert.assertEquals("", context.getAsString("priority"));
        Assert.assertEquals(transition, CommandTransition.SUCCESS);
    }

    @Test
    public void testsetProcessID() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("");
        final DefaultParameterObject context = new DefaultParameterObject();
        final CommandTransition transition = xmlChainBuilder.executeCommand(context);
        Assert.assertEquals(transition, CommandTransition.SUCCESS);
    }

    @Test
    public void testTransitions() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainProcess.xml");
        new DefaultParameterObject();
        final CommandContainer<Object> commands = xmlChainBuilder.buildChain();
        Assert.assertNotNull(commands);
        final ProcessCommand<Object> command = (ProcessCommand<Object>) commands.getCommandByProcessID("Start");
        Assert.assertNotNull(command);
        final List<Transition> transList = command.getTransitionList();
        Assert.assertEquals(2, transList.size());
    }

    @Test
    public void testExecuteCommandSUCCESS() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(transition, CommandTransition.SUCCESS);
    }

    @Test
    public void testExecuteCommandAsChainABORT() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainAbort.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(CommandTransition.DONE, transition);
    }

}
