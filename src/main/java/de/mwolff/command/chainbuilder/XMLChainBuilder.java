package de.mwolff.command.chainbuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import de.mwolff.commons.command.DefaultCommandContainer;
import de.mwolff.commons.command.iface.ChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.Context;
import de.mwolff.commons.command.iface.ProcessCommand;

public class XMLChainBuilder<T extends Context> implements ChainBuilder<T> {

    private static final Logger LOG = Logger.getLogger(XMLChainBuilder.class);

    private final List<Command<Context>> commands = new ArrayList<Command<Context>>();
    private String xmlFileName;

    @Override
    public boolean executeAsChain(T context) {
        CommandContainer<T> chain = null;
        try {
            chain = buildChain();
        } catch (final Exception e) {
            XMLChainBuilder.LOG.error("Operation aborted via command implementation.", e);
            return false;
        }
        return chain.executeAsChain(context);
    }

    @Override
    public void execute(T context) throws CommandException {
        try {
            buildChain().execute(context);
        } catch (final Exception e) {
            // Just log, do nothing else
            XMLChainBuilder.LOG.error("Error while executing chain.", e);

        }
    }

    @Override
    public String executeAsProcess(String startCommand, T context) {
        try {
            return buildChain().executeAsProcess(startCommand, context);
        } catch (CommandException e) {
            LOG.error(e);
            return null;
        }
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommandContainer<T> buildChain() throws CommandException {

        final String resource = xmlFileName;
        final SAXReader reader = new SAXReader();
        final Document document = createXMLStream(reader, resource);
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
    private void createCommandOutOfXML(Document document) throws CommandException {

        final List<Element> list = document.selectNodes("//commandchain/command");

        if (!list.isEmpty()) {
            for (final Element element : list) {
                extractElement(element);
            }
        }
    }

    private void extractElement(final Element element) throws CommandException {
        Command<Context> command = null;
        for (@SuppressWarnings("unchecked")
        final Iterator<Attribute> attributeIterator = element.attributeIterator(); attributeIterator.hasNext();) {
            final Attribute attribute = attributeIterator.next();

            if ("class".equals(attribute.getName())) {
                try {
                    command = createAndAddCommand(attribute.getValue());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    LOG.error(e);
                    throw new CommandException("Error creating class via reflection out of xml definition.", e);
                }
            }

            if ("processid".equals(attribute.getName())) {
                ((ProcessCommand<Context>)command).setProcessID(attribute.getValue());
            }

        }
    }

    @SuppressWarnings("unchecked")
    private Command<Context> createAndAddCommand(String name)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Command<Context> command;
        command = (Command<Context>) Class.forName(name).newInstance();
        commands.add(command);
        return command;
    }

    private Document createXMLStream(SAXReader reader, String resource) throws CommandException {
        Document document;
        final InputStream xmlStream = this.getClass().getResourceAsStream(resource);
        if (xmlStream != null) {
            document = createXMLDocument(reader, xmlStream);
        } else {
            throw new CommandException("Could not read xml file");
        }
        return document;
    }

    private static Document createXMLDocument(SAXReader reader, InputStream xmlStream) throws CommandException {
        Document document = null;
        try {
            document = reader.read(xmlStream);
        } catch (final DocumentException e) {
            throw new CommandException("XML Document could not created", e);
        }
        return document;
    }

    @Override
    public void setProcessID(String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }
}
