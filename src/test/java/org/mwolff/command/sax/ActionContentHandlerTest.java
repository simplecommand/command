/** Simple Command Framework.
 * 
 * Framework for easy building software that fits the SOLID principles.
 * 
 * @author Manfred Wolff <m.wolff@neusta.de>
 * 
 *         Download:
 *         https://mwolff.info/bitbucket/scm/scf/simplecommandframework.git
 * 
 *         Copyright (C) 2018 Manfred Wolff and the simple command community
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

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mwolff.command.extensions.MockitoExtension;
import org.mwolff.command.process.Transition;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.Attributes;

@ExtendWith(MockitoExtension.class)
public class ActionContentHandlerTest {

    @BeforeEach
    void init(@Mock final Attributes atts) {

    }

    @Test
    public void testStartElementAction(@Mock final Attributes atts) throws Exception {
        final ActionContentHandler actionContentHandler = new ActionContentHandler();
        Mockito.when(atts.getValue("id")).thenReturn("Start");
        Mockito.when(atts.getValue("class")).thenReturn("org.mwolff.command.samplecommands.ProcessTestCommandStart");
        Mockito.when(atts.getValue("to")).thenReturn("Next");
        Mockito.when(atts.getValue("name")).thenReturn("OK");
        actionContentHandler.startElement("", "action", "", atts);
        final Action action = (Action) ReflectionTestUtils.getField(actionContentHandler, "action");
        Assert.assertNotNull(action);
        Assert.assertThat(action.getClassname(),
                CoreMatchers.is("org.mwolff.command.samplecommands.ProcessTestCommandStart"));
    }

    @Test
    public void testStartElementTransition(@Mock final Attributes atts) throws Exception {
        final ActionContentHandler actionContentHandler = new ActionContentHandler();
        Mockito.when(atts.getValue("to")).thenReturn("Next");
        Mockito.when(atts.getValue("name")).thenReturn("OK");
        ReflectionTestUtils.setField(actionContentHandler, "action", new Action());
        actionContentHandler.startElement("", "transition", "", atts);
        final Action action = (Action) ReflectionTestUtils.getField(actionContentHandler, "action");
        Assert.assertNotNull(action);
        final Transition transition = action.getTransitions().get(0);
        Assert.assertThat(transition.getTarget(), CoreMatchers.is("Next"));
        Assert.assertThat(transition.getReturnValue(), CoreMatchers.is("OK"));
    }

    @Test
    public void testEndElement() throws Exception {
        final ActionContentHandler actionContentHandler = new ActionContentHandler();
        ReflectionTestUtils.setField(actionContentHandler, "action", new Action());
        actionContentHandler.endElement("", "action", "");
        final List<Action> actions = actionContentHandler.getActions();
        Assert.assertThat(actions.size(), CoreMatchers.is(1));
    }
}
