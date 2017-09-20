package org.mwolff.command.sax;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mwolff.command.process.Transition;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.Attributes;

@RunWith(MockitoJUnitRunner.class)
public class ActionContentHandlerTest {

    @Mock
    Attributes atts;

    @Test
    public void testStartElementAction() throws Exception {
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
    public void testStartElementTransition() throws Exception {
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
