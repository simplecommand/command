package org.mwolff.command.sax;

import java.util.ArrayList;

import org.mwolff.command.process.DefaultTransition;
import org.mwolff.command.process.Transition;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ActionContentHandler implements ContentHandler {

    private ArrayList<Action> actions = new ArrayList<>();

    public ArrayList<Action> getActions() {
        return actions;
    }

    private String     currentValue;
    private Action     action;
    private Transition transition;

    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        if (localName.equals("action")) {
            action = new Action();
            action.setId(atts.getValue("id"));
            action.setClassname(atts.getValue("class"));
        }

        if (localName.equals("transition")) {
            transition = new DefaultTransition();
            transition.setTarget(atts.getValue("to"));
            transition.setReturnValue(atts.getValue("name"));
            action.setTransition(transition);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (localName.equals("action")) {
            actions.add(action);
        }
    }

    public void endDocument() throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }


    public void setDocumentLocator(Locator locator) {
    }

    public void skippedEntity(String name) throws SAXException {
    }

    public void startDocument() throws SAXException {
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
    }

    @Override
    public void processingInstruction(String arg0, String arg1) throws SAXException {
    }
}