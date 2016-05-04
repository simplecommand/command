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

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import de.mwolff.commons.command.DefaultParameterObject;
import de.mwolff.commons.command.iface.ChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;
import de.mwolff.commons.command.iface.ProcessCommand;
import de.mwolff.commons.command.iface.Transition;

public class XMLChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void chainbuilderExists() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>("");
        Assert.assertThat(xmlChainBuilder, CoreMatchers.instanceOf(ChainBuilder.class));
    }

    @Test
    public void createInvalidXMLDocument() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("XML Document could not created");
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/invalidXMLDocument.xml");
        xmlChainBuilder.buildChain();
    }

    @Test
    public void emptyCommand() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainEmpty.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<ParameterObject>> commands = (List<Command<ParameterObject>>) ReflectionTestUtils
                .getField(xmlChainBuilder, "actions");
        Assert.assertThat(returnvalue, Matchers.is(true));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(true));
    }

    @Test
    public void executeWithException() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainOneComandException.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.execute(context);
        Assert.assertEquals("true", context.getAsString("executed"));
    }

    @Test
    public void invalidCommandInserted() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/invalidCommandChain.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean result = xmlChainBuilder.executeAsChain(context);
        Assert.assertThat(result, Matchers.is(false));
    }

    @Test
    public void loadInvalidXMLFileFromInputStream() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Could not read xml file");
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>("/notExists.xml");
        xmlChainBuilder.buildChain();
    }

    @Test
    public void oneCommandInserted() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainOneComandSimple.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<ParameterObject>> commands = (List<Command<ParameterObject>>) ReflectionTestUtils
                .getField(xmlChainBuilder, "actions");
        Assert.assertThat(returnvalue, Matchers.is(true));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(false));
        Assert.assertThat(commands.size(), Matchers.is(1));
    }

    @Test
    public void oneExceptionCommandInserted() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainOneComandException.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<ParameterObject>> commands = (List<Command<ParameterObject>>) ReflectionTestUtils
                .getField(xmlChainBuilder, "actions");
        Assert.assertThat(returnvalue, Matchers.is(false));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(false));
        Assert.assertThat(commands.size(), Matchers.is(1));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainProcess.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - Next - Start - Next - ", processflow);
    }

    @Test
    public void testExecuteAsProcessMethodForBuilderWIthException() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>("notExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.execute(context);
        Assert.assertNull(xmlChainBuilder.getProcessID());
    }

    @Test
    public void testExecuteAsProcessWithException() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainProcessNotExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);

    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.execute(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    @Test
    public void testsetProcessID() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("ProcessID cannot be set on Container.");
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>("");
        xmlChainBuilder.setProcessID("something");
    }

    @Test
    public void testTransitions() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<ParameterObject>(
                "/commandChainProcess.xml");
        new DefaultParameterObject();
        final CommandContainer<ParameterObject> commands = xmlChainBuilder.buildChain();
        Assert.assertNotNull(commands);
        final ProcessCommand<ParameterObject> command = (ProcessCommand<ParameterObject>) commands
                .getCommandByProcessID("Start");
        Assert.assertNotNull(command);
        final List<Transition> transList = command.getTransitionList();
        Assert.assertEquals(2, transList.size());
    }

}
