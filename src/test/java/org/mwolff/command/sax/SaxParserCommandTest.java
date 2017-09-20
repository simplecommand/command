package org.mwolff.command.sax;

import static org.mwolff.command.CommandTransition.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import java.io.InputStream;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.process.Transition;
import org.xml.sax.InputSource;

public class SaxParserCommandTest {

    private CommandTransition startParsing(SaxParameterObject context, String filename) {

        final InputStream inputStream = this.getClass().getResourceAsStream("/" + filename);
        final InputSource inputSource = new InputSource(inputStream);
        context.put(INPUT_SOURCE.toString(), inputSource);

        final SaxParserCommand commandSaxParser = new SaxParserCommand();
        return commandSaxParser.executeCommand(context);
    }

    @Test
    public void testSaxParserFailure() throws Exception {
        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "invalidXMLDocument.xml");
        Assert.assertThat(result, CoreMatchers.is(FAILURE));
        // Because of different OS (English vs. German) you cannot parse the actual error message.
        Assert.assertThat(context.getAsString(ERROR_STRING.toString()), CoreMatchers.is(CoreMatchers.not("")));
    }

    @Test
    public void testSingleActionWithClassName() throws Exception {

        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "commandChainOneComandSimple.xml");

        @SuppressWarnings("unchecked")
        final List<Action> actions = (List<Action>) context.get(ACTION_LIST);
        Assert.assertThat(actions.size(), CoreMatchers.is(1));
        Assert.assertThat(actions.get(0).getId(), CoreMatchers.nullValue());
        Assert.assertThat(actions.get(0).getClassname(),
                CoreMatchers.is("org.mwolff.command.samplecommands.SimpleTestCommand"));
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
    }

    @Test
    public void testSingleActionWithClassNameAndId() throws Exception {

        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "commandChainProcess.xml");

        @SuppressWarnings("unchecked")
        final List<Action> actions = (List<Action>) context.get(ACTION_LIST);
        Assert.assertThat(actions.size(), CoreMatchers.is(2));
        Assert.assertThat(actions.get(0).getId(), CoreMatchers.is("Start"));
        Assert.assertThat(actions.get(0).getClassname(),
                CoreMatchers.is("org.mwolff.command.samplecommands.ProcessTestCommandStart"));
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
    }

    @Test
    public void testActionWithTransition() throws Exception {
        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "commandChainProcess.xml");

        @SuppressWarnings("unchecked")
        final List<Action> actions = (List<Action>) context.get(ACTION_LIST);
        final Transition transition = actions.get(0).getTransitions().get(0);
        Assert.assertThat(transition.getTarget(), CoreMatchers.is("Next"));
        Assert.assertThat(transition.getReturnValue(), CoreMatchers.is("OK"));
        Assert.assertThat(result, CoreMatchers.is(SUCCESS));
    }
}
