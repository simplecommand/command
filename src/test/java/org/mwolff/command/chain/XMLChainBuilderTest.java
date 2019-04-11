/*         Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
 *
 *         This library is free software; you can redistribute it and/or
 *         modify it under the terms of the GNU Lesser General Public
 *         License as published by the Free Software Foundation; either
 *         version 2.1 of the License, or (at your option) any later version.
 *
 *         This library is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         Lesser General Public License for more details.
 *
 *         You should have received a copy of the GNU Lesser General Public
 *         License along with this library; if not, write to the Free Software
 *         Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *         02110-1301 USA
 */
package org.mwolff.command.chain;

import static org.mwolff.command.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import java.util.Optional;

public class XMLChainBuilderTest {

    @Test
    public void testUnUsedSetProcessID() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("commandChainProcess.xml");

        final Throwable exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            xmlChainBuilder.setProcessID("");
        });
        Assert.assertThat(exception.getMessage(), CoreMatchers.is("Chainbuilder has no process id."));
    }

    @Test
    public void chainbuilderExists() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("");
        Assert.assertThat(xmlChainBuilder, CoreMatchers.instanceOf(XMLChainBuilder.class));
    }

    @Test
    public void createInvalidXMLDocument() throws Exception {
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        final XMLChainBuilder<GenericParameterObject> xmlChainBuilder = new XMLChainBuilder<>("invalidXMLDocument.xml");
        final CommandTransition result = xmlChainBuilder.executeCommand(context);
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilderWIthException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("notExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.executeCommand(context);
        Assert.assertNull(xmlChainBuilder.getProcessID());
    }

    @Test
    public void testExecuteAsProcessWithException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainProcessNotExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final Optional<String> result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteAsProcess() throws Exception {
        final XMLChainBuilder<GenericParameterObject> builder = new XMLChainBuilder<>("/commandChainProcess.xml");
        final Throwable exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            builder.executeAsProcess(DefaultParameterObject.NULLCONTEXT);
        });
        Assert.assertThat(exception.getMessage(), CoreMatchers.is("Use executeAsProcess(String start, T context"));
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("resultString", "");
        final CommandTransition transition = xmlChainBuilder.executeCommand(context);
        Assert.assertEquals("S-S-", context.getAsString("resultString"));
        Assert.assertEquals(transition, SUCCESS);
    }

    @Test
    public void testExecuteCommandSUCCESS() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(transition, DONE);
    }

    @Test
    public void testExecuteCommandAsChainABORT() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainAbort.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(FAILURE, transition);
    }
}
