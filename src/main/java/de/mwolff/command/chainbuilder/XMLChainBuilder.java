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
import de.mwolff.commons.command.DefaultTransition;
import de.mwolff.commons.command.iface.ChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;
import de.mwolff.commons.command.iface.ProcessCommand;
import de.mwolff.commons.command.iface.Transition;

public class XMLChainBuilder<T extends ParameterObject> implements ChainBuilder<T> {

    private static final Logger LOG = Logger.getLogger(XMLChainBuilder.class);
    private static final String ROOT = "//process/action";
    private static final String TRANS = "//process/action[@id='%s']/transition";
    private static final String CLASS = "class";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TO = "to";
    private String xmlFileName;
    
    private final List<Command<ParameterObject>> commands = new ArrayList<Command<ParameterObject>>();
    Document document = null;

    public XMLChainBuilder(final String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    @Deprecated
    /**
     * Use XMLChainBuilder(final String xmlFileName) instead.
     */
    public XMLChainBuilder() {
        super();
    }

    @Deprecated
    /**
     * Use XMLChainBuilder(final String xmlFileName) instead.
     * 
     * @param xmlFileName
     */
    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
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
    public String executeAsProcess(String startCommand, T context) {
        try {
            return buildChain().executeAsProcess(startCommand, context);
        } catch (final CommandException e) {
            LOG.error(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommandContainer<T> buildChain() throws CommandException {

        final String resource = xmlFileName;
        final SAXReader reader = new SAXReader();
        createXMLStream(reader, resource);
        createCommandOutOfXML(document);

        final CommandContainer<T> commandContainer = new DefaultCommandContainer<T>();
        for (final Command<ParameterObject> command : commands) {
            commandContainer.addCommand((Command<T>) command);
        }
        return commandContainer;
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @Override
    public void setProcessID(String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }

    private void createXMLStream(SAXReader reader, String resource) throws CommandException {
        final InputStream xmlStream = this.getClass().getResourceAsStream(resource);
        if (xmlStream != null) {
            createXMLDocument(reader, xmlStream);
        } else {
            throw new CommandException("Could not read xml file");
        }
    }

    private void createXMLDocument(SAXReader reader, InputStream xmlStream) throws CommandException {
        try {
            document = reader.read(xmlStream);
        } catch (final DocumentException e) {
            throw new CommandException("XML Document could not created", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void createCommandOutOfXML(Document document) throws CommandException {

        final List<Element> list = document.selectNodes(ROOT);

        if (!list.isEmpty()) {
            for (final Element element : list) {
                extractCommandElement(element);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Command<ParameterObject> createAndAddCommand(String name)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Command<ParameterObject> command;
        command = (Command<ParameterObject>) Class.forName(name).newInstance();
        commands.add(command);
        return command;
    }

    @SuppressWarnings("unchecked")
    private void extractCommandElement(final Element element) throws CommandException {

        Command<ParameterObject> command = null;
        String commandID = null;

        for (final Iterator<Attribute> attributeIterator = element.attributeIterator(); attributeIterator.hasNext();) {
            final Attribute attribute = attributeIterator.next();

            if (CLASS.equals(attribute.getName())) {
                try {
                    command = createAndAddCommand(attribute.getValue());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    LOG.error(e);
                    throw new CommandException("Error creating class via reflection out of xml definition.", e);
                }
            }

            if (ID.equals(attribute.getName())) {
                ((ProcessCommand<ParameterObject>) command).setProcessID(attribute.getValue());
                commandID = attribute.getValue();
            }
        }

        if (commandID != null) {
            extractTransitionElements((ProcessCommand<ParameterObject>) command, commandID);
        }
    }

    @SuppressWarnings("unchecked")
    private void extractTransitionElements(ProcessCommand<ParameterObject> command, String commandID) {
        final List<Element> transitionElementList = document.selectNodes(String.format(TRANS, commandID));
        for (final Element transition : transitionElementList) {
            final Transition transitionClass = new DefaultTransition();
            for (final Iterator<Attribute> attributeIterator = transition.attributeIterator(); attributeIterator
                    .hasNext();) {
                final Attribute attribute = attributeIterator.next();

                if (NAME.equals(attribute.getName())) {
                    transitionClass.setReturnValue(attribute.getValue());
                }

                if (TO.equals(attribute.getName())) {
                    transitionClass.setTarget(attribute.getValue());
                }
            }
            command.addTransition(transitionClass);
        }
    }

}
