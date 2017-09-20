package org.mwolff.command.sax;

import java.util.ArrayList;
import java.util.List;

import org.mwolff.command.process.DefaultTransition;
import org.mwolff.command.process.Transition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ActionContentHandler extends DefaultHandler {

    private final ArrayList<Action> actions = new ArrayList<>();
    private Action     action;
    private Transition transition;

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        if ("action".equals(localName)) {
            action = new Action();
            action.setId(atts.getValue("id"));
            action.setClassname(atts.getValue("class"));
        }

        if ("transition".equals(localName)) {
            transition = new DefaultTransition();
            transition.setTarget(atts.getValue("to"));
            transition.setReturnValue(atts.getValue("name"));
            action.setTransition(transition);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if ("action".equals(localName)) {
            actions.add(action);
        }
    }
}