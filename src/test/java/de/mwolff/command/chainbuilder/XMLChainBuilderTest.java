package de.mwolff.command.chainbuilder;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import de.mwolff.commons.command.DefaultContext;
import de.mwolff.commons.command.iface.ChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.Context;

public class XMLChainBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void chainbuilderExists() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        Assert.assertThat(xmlChainBuilder, CoreMatchers.instanceOf(ChainBuilder.class));
    }

    @Test
    public void loadInvalidXMLFileFromInputStream() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Could not read xml file");
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        xmlChainBuilder.setXmlFileName("/notExists.xml");
        xmlChainBuilder.buildChain();
    }

    @Test
    public void createInvalidXMLDocument() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("XML Document could not created");
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        xmlChainBuilder.setXmlFileName("/invalidXMLDocument.xml");
        xmlChainBuilder.buildChain();
    }

    @Test
    public void oneCommandInserted() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainOneComandSimple.xml");
        boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Context>> commands = (List<Command<Context>>) ReflectionTestUtils.getField(xmlChainBuilder,
                "commands");
        Assert.assertThat(returnvalue, Matchers.is(true));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(false));
        Assert.assertThat(commands.size(), Matchers.is(1));
    }

    @Test
    public void emptyCommand() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainEmpty.xml");
        boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Context>> commands = (List<Command<Context>>) ReflectionTestUtils.getField(xmlChainBuilder,
                "commands");
        Assert.assertThat(returnvalue, Matchers.is(true));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(true));
    }

    @Test
    public void oneExceptionCommandInserted() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainOneComandException.xml");
        boolean returnvalue = xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Context>> commands = (List<Command<Context>>) ReflectionTestUtils.getField(xmlChainBuilder,
                "commands");
        Assert.assertThat(returnvalue, Matchers.is(false));
        Assert.assertThat(commands, Matchers.notNullValue());
        Assert.assertThat(commands.isEmpty(), Matchers.is(false));
        Assert.assertThat(commands.size(), Matchers.is(1));
    }

    @Test
    public void invalidCommandInserted() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/invalidCommandChain.xml");
        final boolean result = xmlChainBuilder.executeAsChain(context);
        Assert.assertThat(result, Matchers.is(false));
    }

    @Test
    public void testExecuteMethodForBuilder() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainPriority.xml");
        xmlChainBuilder.execute(context);
        Assert.assertEquals("1-2-", context.getAsString("priority"));
    }

    @Test
    public void executeWithException() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainOneComandException.xml");
        xmlChainBuilder.execute(context);
        Assert.assertEquals("true", context.getAsString("executed"));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilder() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainProcess.xml");
        Assert.assertNull(xmlChainBuilder.executeAsProcess("Start", context));
    }

    @Test
    public void testExecuteAsProcessMethodForBuilderWIthException() throws Exception {
        final XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        final DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("notExists.xml");
        xmlChainBuilder.execute(context);
        Assert.assertNull(xmlChainBuilder.getProcessID());
    }
}
