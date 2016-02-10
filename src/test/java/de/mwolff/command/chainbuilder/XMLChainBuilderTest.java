package de.mwolff.command.chainbuilder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import de.mwolff.commons.command.DefaultContext;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.Context;

public class XMLChainBuilderTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void chainbuilderExists() throws Exception {
		XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
		assertThat(xmlChainBuilder, CoreMatchers.instanceOf(ChainBuilder.class));
	}
		
	@Test
	public void loadInvalidXMLFileFromInputStream() throws Exception {
		thrown.expect(CommandException.class);
		thrown.expectMessage("Could not read xml file");
		XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
		xmlChainBuilder.setXmlFileName("/notExists.xml");
		xmlChainBuilder.buildChain();
	}

	@Test
	public void createInvalidXMLDocument() throws Exception {
		thrown.expect(CommandException.class);
		thrown.expectMessage("XML Document could not created");
		XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
		xmlChainBuilder.setXmlFileName("/invalidXMLDocument.xml");
		xmlChainBuilder.buildChain();
	}
	
	@Test
	public void oneCommandInserted() throws Exception {
		XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
		DefaultContext context = new DefaultContext();
		xmlChainBuilder.setXmlFileName("/commandChainOneComandSimple.xml");
		xmlChainBuilder.executeAsChain(context);
		@SuppressWarnings("unchecked")
		final List<Command<Context>> commands = (List<Command<Context>>) ReflectionTestUtils
				.getField(xmlChainBuilder, "commands");
		assertThat(commands, notNullValue());
		assertThat(commands.isEmpty(), is(false));
		assertThat(commands.size(), is(1));
	}

	@Test
	public void emptyCommand() throws Exception {
		XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
		DefaultContext context = new DefaultContext();
		xmlChainBuilder.setXmlFileName("/commandChainEmpty.xml");
		xmlChainBuilder.executeAsChain(context);
		@SuppressWarnings("unchecked")
		final List<Command<Context>> commands = (List<Command<Context>>) ReflectionTestUtils
				.getField(xmlChainBuilder, "commands");
		assertThat(commands, notNullValue());
		assertThat(commands.isEmpty(), is(true));
	}

    @Test
    public void oneExceptionCommandInserted() throws Exception {
        XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/commandChainOneComandException.xml");
        xmlChainBuilder.executeAsChain(context);
        @SuppressWarnings("unchecked")
        final List<Command<Context>> commands = (List<Command<Context>>) ReflectionTestUtils
                .getField(xmlChainBuilder, "commands");
        assertThat(commands, notNullValue());
        assertThat(commands.isEmpty(), is(false));
        assertThat(commands.size(), is(1));
    }

    @Test
    public void invalidCommandInserted() throws Exception {
        XMLChainBuilder<Context> xmlChainBuilder = new XMLChainBuilder<Context>();
        DefaultContext context = new DefaultContext();
        xmlChainBuilder.setXmlFileName("/invalidCommandChain.xml.xml");
        boolean result = xmlChainBuilder.executeAsChain(context);
        assertThat(result, is(false));
    }
}
