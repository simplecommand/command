package org.mwolff.command.sax;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.CommandTransitionEnum.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.Transition;
import org.xml.sax.InputSource;

public class SaxParserCommandTest {

    private CommandTransition startParsing(GenericParameterObject context, String filename) {

        final InputStream inputStream = this.getClass()
                .getResourceAsStream("/" + filename);
        InputSource inputSource = new InputSource(inputStream);
        context.put(input_source, inputSource);

        SaxParserCommand<GenericParameterObject> commandSaxParser = new SaxParserCommand<>();
        return commandSaxParser.executeCommand(context);
    }

    @Test
    public void testSaxParserFailure() throws Exception {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        CommandTransition result = startParsing(context, "invalidXMLDocument.xml");
        assertThat(result, is(FAILURE));
        // Because of different OS (English vs. German) you cannot parse the actual error message.
        assertThat(context.getAsString(error_string), is(not("")));
    }

    @Test
    public void testSingleActionWithClassName() throws Exception {

        GenericParameterObject context = DefaultParameterObject.getInstance();
        CommandTransition result = startParsing(context, "commandChainOneComandSimple.xml");

        @SuppressWarnings("unchecked")
        List<Action> actions = (List<Action>) context.get(action_list);
        assertThat(actions.size(), is(1));
        assertThat(actions.get(0).getId(), nullValue());
        assertThat(actions.get(0).getClassname(), is("org.mwolff.command.samplecommands.SimpleTestCommand"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testSingleActionWithClassNameAndId() throws Exception {

        GenericParameterObject context = DefaultParameterObject.getInstance();
        CommandTransition result = startParsing(context, "commandChainProcess.xml");

        @SuppressWarnings("unchecked")
        List<Action> actions = (List<Action>) context.get(action_list);
        assertThat(actions.size(), is(2));
        assertThat(actions.get(0).getId(), is("Start"));
        assertThat(actions.get(0).getClassname(), is("org.mwolff.command.samplecommands.ProcessTestCommandStart"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testActionWithTransition() throws Exception {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        CommandTransition result = startParsing(context, "commandChainProcess.xml");

        @SuppressWarnings("unchecked")
        List<Action> actions = (List<Action>) context.get(action_list);
        Transition transition = actions.get(0).getTransitions().get(0);
        assertThat(transition.getTarget(), is("Next"));
        assertThat(transition.getReturnValue(), is("OK"));
        assertThat(result, is(SUCCESS));
    }
}
