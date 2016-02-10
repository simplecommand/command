package de.mwolff.command.chainbuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import de.mwolff.commons.command.DefaultCommandContainer;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.Context;

public class XMLChainBuilder<T extends Context>  implements ChainBuilder<T>{

	private List<Command<Context>> commands = new ArrayList<Command<Context>>();
	private String xmlFileName;

	@Override
	public boolean executeAsChain(T context) {
		CommandContainer<T> chain = null;
		try {
			chain = buildChain();
		} catch (Exception e) {
			return false;
		}
		return chain.executeAsChain(context);
	}
	
	@Override
	public void execute(T context) throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String executeAsProcess(String startCommand, T context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessID() {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public CommandContainer<T> buildChain() throws CommandException, Exception {

		String resource = xmlFileName;
		SAXReader reader = new SAXReader();
		Document document = createXMLStream(reader, resource);
		createCommandOutOfXML(document);

		final CommandContainer<T> commandContainer = new DefaultCommandContainer<T>();
		for (final Command<Context> command : commands) {
			commandContainer.addCommand((Command<T>) command);
		}
		return commandContainer;
	}

	public void setXmlFileName(String xmlFileName) {
	    this.xmlFileName = xmlFileName;
	}

	@SuppressWarnings("unchecked")
	private void createCommandOutOfXML(Document document)
			throws CommandException, Exception {
		
		List<Element> list = document.selectNodes("//commandchain/command");

		if (!list.isEmpty()) {
			for (Iterator<Element> elementIterator = list.iterator(); elementIterator.hasNext();) {
				Element element = elementIterator.next();
				for (Iterator<Attribute> attributeIterator = element.attributeIterator(); attributeIterator
						.hasNext();) {
					Attribute attribute = attributeIterator.next();
					String name = attribute.getValue();
					createAndAddCommand(name);
				}			}
		}
	}

	@SuppressWarnings("unchecked")
	private void createAndAddCommand(String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Command<Context> command = null;
		command = (Command<Context>) Class.forName(name)
				.newInstance();
		commands.add(command);
	}

	private Document createXMLStream(SAXReader reader, String resource)
			throws CommandException {
		Document document;
		InputStream xmlStream = this.getClass().getResourceAsStream(resource);
		if (xmlStream != null) {
			document = createXMLDocument(reader, xmlStream);
		} else {
			throw new CommandException("Could not read xml file");
		}
		return document;
	}

	private Document createXMLDocument(SAXReader reader, InputStream xmlStream)
			throws CommandException {
		Document document = null;
		try {
			document = reader.read(xmlStream);
		} catch (DocumentException e) {
			throw new CommandException("XML Document could not created", e);
		}
		return document;
	}
}
