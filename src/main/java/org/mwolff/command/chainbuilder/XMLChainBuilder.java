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
package org.mwolff.command.chainbuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mwolff.commons.command.DefaultCommandContainer;
import org.mwolff.commons.command.DefaultTransition;
import org.mwolff.commons.command.iface.ChainBuilder;
import org.mwolff.commons.command.iface.Command;
import org.mwolff.commons.command.iface.CommandContainer;
import org.mwolff.commons.command.iface.CommandException;
import org.mwolff.commons.command.iface.ParameterObject;
import org.mwolff.commons.command.iface.ProcessCommand;
import org.mwolff.commons.command.iface.Transition;

/**
 * Chain builder parsing an XML file for building chains or process chains.
 *
 * @author Manfred Wolff
 */
public class XMLChainBuilder<T extends ParameterObject> implements ChainBuilder<T> {

    private static final Logger LOG = Logger.getLogger(XMLChainBuilder.class);
    private static final String ROOT = "//process/action";
    private static final String TRANS = "//process/action[@id='%s']/transition";
    private static final String CLASS = "class";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TO = "to";
    private String xmlFileName;

    private final List<Command<ParameterObject>> actions;
    Document document;

    /**
     * Constructor.
     *
     * @param xmlFileName
     *            Filename of the XML file. File has to reachable during
     *            CLASSPATH.
     */
    public XMLChainBuilder(final String xmlFileName) {
        this.xmlFileName = xmlFileName;
        actions = new ArrayList<>();
    }

    // **** Methods from super type *****

    /*
     * @see de.mwolff.commons.command.iface.ChainBuilder#buildChain()
     */
    @Override
    public CommandContainer<T> buildChain() throws CommandException {

        final String resource = xmlFileName;
        final SAXReader reader = new SAXReader();
        createXMLStream(reader, resource);
        createCommandsOutOfXML(document);

        return addCommandsToCommandContainer();
    }

    /*
     * @see
     * de.mwolff.commons.command.iface.Command#execute(de.mwolff.commons.command
     * .iface.ParameterObject)
     */
    @Override
    public void execute(final T context) throws CommandException {
        try {
            buildChain().execute(context);
        } catch (final Exception e) {
            // Just log, do nothing else
            XMLChainBuilder.LOG.error("Error while executing chain.", e);

        }
    }

    /*
     * @see
     * de.mwolff.commons.command.iface.ChainCommand#executeAsChain(de.mwolff.
     * commons.command.iface.ParameterObject)
     */
    @Override
    public boolean executeAsChain(final T context) {
        CommandContainer<T> chain = null;
        try {
            chain = buildChain();
        } catch (final Exception e) {
            XMLChainBuilder.LOG.error("Operation aborted via command implementation.", e);
            return false;
        }
        return chain.executeAsChain(context);
    }

    /*
     * @see
     * de.mwolff.commons.command.iface.ProcessCommand#executeAsProcess(java.lang
     * .String, de.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        try {
            return buildChain().executeAsProcess(startCommand, context);
        } catch (final CommandException e) {
            LOG.error(e);
            return null;
        }
    }

    /*
     * @see de.mwolff.commons.command.iface.ProcessCommand#getProcessID()
     */
    @Override
    public String getProcessID() {
        return null;
    }

    /*
     * @see
     * de.mwolff.commons.command.iface.ProcessCommand#setProcessID(java.lang.
     * String)
     */
    @Override
    public void setProcessID(final String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }

    @SuppressWarnings("unchecked")
    private void createCommandsOutOfXML(final Document document) throws CommandException {

        final List<Element> list = document.selectNodes(ROOT);

        if (!list.isEmpty()) {
            for (final Element element : list) {
                extractAttributesOfActionElement(element);
            }
        }
    }

    private void readXMLDocument(final SAXReader reader, final InputStream xmlStream) throws CommandException {
        try {
            document = reader.read(xmlStream);
        } catch (final DocumentException e) {
            throw new CommandException("XML Document could not created", e);
        }
    }

    @SuppressWarnings("unchecked")
    private CommandContainer<T> addCommandsToCommandContainer() {
        final CommandContainer<T> commandContainer = new DefaultCommandContainer<>();
        for (final Command<ParameterObject> command : actions) {
            commandContainer.addCommand((Command<T>) command);
        }
        return commandContainer;
    }

    @SuppressWarnings("unchecked")
    private Command<ParameterObject> createAndAddAction(final String name)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Command<ParameterObject> command;
        command = (Command<ParameterObject>) Class.forName(name).newInstance();
        actions.add(command);
        return command;
    }

    private void createXMLStream(final SAXReader reader, final String resource) throws CommandException {
        final InputStream xmlStream = this.getClass().getResourceAsStream(resource);
        if (xmlStream != null) {
            readXMLDocument(reader, xmlStream);
        } else {
            throw new CommandException("Could not read xml file");
        }
    }

    private void extractAttributesOfActionElement(final Element element) throws CommandException {

        Command<ParameterObject> action = null;
        String actionID = null;

        final Map<String, String> attributeMap = getAttributeOfElement(element);

        String value = attributeMap.get(CLASS);
        try {
            action = createAndAddAction(value);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            LOG.error(e);
            throw new CommandException("Error creating class via reflection out of xml definition.", e);
        }

        value = attributeMap.get(ID);
        if (value != null) {
            ((ProcessCommand<ParameterObject>) action).setProcessID(value);
            actionID = value;
        }

        if (actionID != null) {
            extractAttributesOfTransitionElement((ProcessCommand<ParameterObject>) action, actionID);
        }
    }

    private static Map<String, String> getAttributeOfElement(final Element element) {
        final Map<String, String> attributeMap = new HashMap<>();
        for (int i = 0; i < element.attributeCount(); i++) {
            attributeMap.put(element.attribute(i).getName(), element.attribute(i).getValue());
        }
        return attributeMap;
    }

    @SuppressWarnings("unchecked")
    private void extractAttributesOfTransitionElement(final ProcessCommand<ParameterObject> command,
            final String commandID) {

        final List<Element> transitionElementList = document.selectNodes(String.format(TRANS, commandID));

        for (final Element transition : transitionElementList) {
            final Transition transitionClass = new DefaultTransition();

            final Map<String, String> attributeMap = getAttributeOfElement(transition);

            String value = attributeMap.get(NAME);
            transitionClass.setReturnValue(value);

            value = attributeMap.get(TO);
            transitionClass.setTarget(value);

            command.addTransition(transitionClass);
        }
    }
}
