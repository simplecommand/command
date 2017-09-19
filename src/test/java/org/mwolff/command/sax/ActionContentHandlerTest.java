package org.mwolff.command.sax;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.CoreMatchers;
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
        ActionContentHandler actionContentHandler = new ActionContentHandler();
        Mockito.when(atts.getValue("id")).thenReturn("Start");
        Mockito.when(atts.getValue("class")).thenReturn("org.mwolff.command.samplecommands.ProcessTestCommandStart");
        Mockito.when(atts.getValue("to")).thenReturn("Next");
        Mockito.when(atts.getValue("name")).thenReturn("OK");
        actionContentHandler.startElement("","action", "", atts);
        Action action = (Action)ReflectionTestUtils.getField(actionContentHandler, "action");
        assertNotNull(action);
        assertThat(action.getClassname(), is("org.mwolff.command.samplecommands.ProcessTestCommandStart"));
    }

    @Test
    public void testStartElementTransition() throws Exception {
        ActionContentHandler actionContentHandler = new ActionContentHandler();
        Mockito.when(atts.getValue("to")).thenReturn("Next");
        Mockito.when(atts.getValue("name")).thenReturn("OK");
        ReflectionTestUtils.setField(actionContentHandler, "action", new Action());
        actionContentHandler.startElement("","transition", "", atts);
        Action action = (Action)ReflectionTestUtils.getField(actionContentHandler, "action");
        assertNotNull(action);
        Transition transition = action.getTransitions().get(0);
        assertThat(transition.getTarget(), CoreMatchers.is("Next"));
        assertThat(transition.getReturnValue(), is("OK"));
    }
    
    @Test
    public void testEndElement() throws Exception {
        ActionContentHandler actionContentHandler = new ActionContentHandler();
        ReflectionTestUtils.setField(actionContentHandler, "action", new Action());
        actionContentHandler.endElement("", "action", "");
        List<Action> actions = actionContentHandler.getActions();
        assertThat(actions.size(), is(1));
    }
}
