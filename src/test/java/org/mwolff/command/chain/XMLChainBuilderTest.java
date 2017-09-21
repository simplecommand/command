package org.mwolff.command.chain;

import static org.mwolff.command.CommandTransition.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class XMLChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testUnUsedSetProcessID() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Chainbuilder has no process id.");
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("commandChainProcess.xml");
        xmlChainBuilder.setProcessID("");
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
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        Assert.assertNull(result);
    }

    @Test
    public void testExecuteAsProcess() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        final XMLChainBuilder<GenericParameterObject> builder = new XMLChainBuilder<>("/commandChainProcess.xml");
        builder.executeAsProcess(DefaultParameterObject.NULLCONTEXT);
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommand(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
        Assert.assertEquals(transition, SUCCESS);
    }

    @Test
    public void testExecuteCommandSUCCESS() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(transition, SUCCESS);
    }

    @Test
    public void testExecuteCommandAsChainABORT() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainAbort.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        Assert.assertEquals(FAILURE, transition);
    }
}
