package org.mwolff.command.chain;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mwolff.command.CommandTransition.*;

public class XMLChainBuilderTest {

    @Test
    public void testUnUsedSetProcessID() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("commandChainProcess.xml");

        final Throwable exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            xmlChainBuilder.setProcessID("");
        });
        assertThat(exception.getMessage(), CoreMatchers.is("Chainbuilder has no process id."));
    }

    @Test
    public void chainbuilderExists() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("");
        assertThat(xmlChainBuilder, CoreMatchers.instanceOf(XMLChainBuilder.class));
    }

    @Test
    public void createInvalidXMLDocument() throws Exception {
        final GenericParameterObject context = DefaultParameterObject.getInstance();
        final XMLChainBuilder<GenericParameterObject> xmlChainBuilder = new XMLChainBuilder<>("invalidXMLDocument.xml");
        final CommandTransition result = xmlChainBuilder.executeCommand(context);
        assertThat(result, CoreMatchers.is(FAILURE));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilderWIthException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("notExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        xmlChainBuilder.executeCommand(context);
        assertNull(xmlChainBuilder.getProcessID());
    }

    @Test
    public void testExecuteAsProcessWithException() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainProcessNotExists.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("Start", context);
        assertNull(result);
    }

    @Test
    public void testExecuteAsProcess() throws Exception {
        final XMLChainBuilder<GenericParameterObject> builder = new XMLChainBuilder<>("/commandChainProcess.xml");
        final Throwable exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            builder.executeAsProcess(new DefaultParameterObject());
        });
        assertThat(exception.getMessage(), CoreMatchers.is("Use executeAsProcess(String start, T context"));
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainPriority.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("resultString", "");
        final CommandTransition transition = xmlChainBuilder.executeCommand(context);
        assertEquals("S-S-", context.getAsString("resultString"));
        assertEquals(transition, SUCCESS);
    }

    @Test
    public void testExecuteCommandSUCCESS() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        context.put("priority", "");
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        assertEquals(transition, DONE);
    }

    @Test
    public void testExecuteCommandAsChainABORT() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>("/commandChainAbort.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final CommandTransition transition = xmlChainBuilder.executeCommandAsChain(context);
        assertEquals(FAILURE, transition);
    }
}
