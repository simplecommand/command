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

import org.mwolff.command.AbstractDefaultCommand;
import org.mwolff.command.interfaces.CommandTransition;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

import static org.mwolff.command.interfaces.CommandTransition.FAILURE;
import static org.mwolff.command.interfaces.CommandTransition.SUCCESS;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

public class SaxParserCommand extends AbstractDefaultCommand<SaxParameterObject> {

    @Override
    public CommandTransition executeCommand(SaxParameterObject parameterObject) {

        try {
            final InputSource inputSource = (InputSource) parameterObject.get(INPUT_SOURCE);

            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            final XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
            xmlReader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
            xmlReader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
            final ActionContentHandler handler = new ActionContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);
            parameterObject.put(ACTION_LIST, handler.getActions());

        } catch (IOException | SAXException | ParserConfigurationException e) {
            parameterObject.put(ERROR_STRING, e.getMessage());
            return FAILURE;
        }
        return SUCCESS;
    }

}
