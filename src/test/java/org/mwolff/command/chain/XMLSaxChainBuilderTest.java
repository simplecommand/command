package org.mwolff.command.chain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class XMLSaxChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDeprecatedExecute() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Use executeCommand instead.");
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("commandChainProcess.xml");
        xmlChainBuilder.execute(DefaultParameterObject.NULLCONTEXT);
    }

    @Test
    public void testDeprecatedExecuteAsChain() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Use executeCommandAsChain instead.");
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("commandChainProcess.xml");
        xmlChainBuilder.executeAsChain(DefaultParameterObject.NULLCONTEXT);
    }

    @Test
    public void chainbuilderExists() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("");
        xmlChainBuilder.setProcessID("");
        Assert.assertThat(xmlChainBuilder, CoreMatchers.instanceOf(XMLSaxChainBuilder.class));
    }

    @Test
    public void createInvalidXMLDocument() throws Exception {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("/invalidXMLDocument.xml");
        CommandTransition result = xmlChainBuilder.executeCommand(context);
        assertThat(result, is(FAILURE));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("commandChainProcess.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - Next - Start - Next - ", processflow);
    }

    @Test
    public void testExecuteAsProcessMethodForBuilderWIthException() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("notExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.executeCommand(context);
        Assert.assertNull(xmlChainBuilder.getProcessID());
    }

    @Test
    public void testExecuteAsProcessWithException() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>(
                "/commandChainProcessNotExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteAsProcess() throws Exception {
        final XMLSaxChainBuilder<GenericParameterObject> builder = new XMLSaxChainBuilder<>("/commandChainProcess.xml");
        final String result = builder.executeAsProcess(DefaultParameterObject.NULLCONTEXT);
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        xmlChainBuilder.executeCommand(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommand(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
        Assert.assertEquals(transition, CommandTransition.SUCCESS);
    }

    @Test
    public void testExecuteCommandSUCCESS() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(transition, CommandTransition.SUCCESS);
    }

    @Test
    public void testExecuteCommandAsChainABORT() throws Exception {
        final XMLSaxChainBuilder<Object> xmlChainBuilder = new XMLSaxChainBuilder<>("/commandChainAbort.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(CommandTransition.FAILURE, transition);
    }

}
