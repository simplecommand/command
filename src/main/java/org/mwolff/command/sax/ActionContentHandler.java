/** Simple Command Framework.
 *
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Download:
 *         https://github.com/simplecommand/command.git
 *
 *         Copyright (C) 2018-2021 Manfred Wolff and the simple command community
 *
 *         This library is free software; you can redistribute it and/or
 *         modify it under the terms of the GNU Lesser General Public
 *         License as published by the Free Software Foundation; either
 *         version 2.1 of the License, or (at your option) any later version.
 *
 *         This library is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         Lesser General Public License for more details.
 *
 *         You should have received a copy of the GNU Lesser General Public
 *         License along with this library; if not, write to the Free Software
 *         Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *         02110-1301
 *         USA */

package org.mwolff.command.sax;

import org.mwolff.command.interfaces.Transition;
import org.mwolff.command.process.DefaultTransition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ActionContentHandler extends DefaultHandler {

    private final ArrayList<Action> actions = new ArrayList<>();
    private Action                  action;

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        Transition transition;
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