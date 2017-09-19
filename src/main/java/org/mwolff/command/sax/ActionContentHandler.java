package org.mwolff.command.sax;

import java.util.ArrayList;

import org.mwolff.command.process.DefaultTransition;
import org.mwolff.command.process.Transition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ActionContentHandler extends DefaultHandler {

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
}