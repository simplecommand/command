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

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.process.Transition;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mwolff.command.CommandTransition.FAILURE;
import static org.mwolff.command.CommandTransition.SUCCESS;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

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
        assertThat(result, CoreMatchers.is(FAILURE));
        // Because of different OS (English vs. German) you cannot parse the
        // actual error message.
        assertThat(context.getAsString(ERROR_STRING.toString()), CoreMatchers.is(CoreMatchers.not("")));
    }

    @Test
    public void testSingleActionWithClassName() throws Exception {

        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "commandChainOneComandSimple.xml");

        @SuppressWarnings("unchecked")
        final List<Action> actions = (List<Action>) context.get(ACTION_LIST);
        assertThat(actions.size(), CoreMatchers.is(1));
        assertThat(actions.get(0).getId(), CoreMatchers.nullValue());
        assertThat(actions.get(0).getClassname(),
                CoreMatchers.is("org.mwolff.command.samplecommands.SimpleTestCommand"));
        assertThat(result, CoreMatchers.is(SUCCESS));
    }

    @Test
    public void testSingleActionWithClassNameAndId() throws Exception {

        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "commandChainProcess.xml");

        @SuppressWarnings("unchecked")
        final List<Action> actions = (List<Action>) context.get(ACTION_LIST);
        assertThat(actions.size(), CoreMatchers.is(2));
        assertThat(actions.get(0).getId(), CoreMatchers.is("Start"));
        assertThat(actions.get(0).getClassname(),
                CoreMatchers.is("org.mwolff.command.samplecommands.ProcessTestCommandStart"));
        assertThat(result, CoreMatchers.is(SUCCESS));
    }

    @Test
    public void testActionWithTransition() throws Exception {
        final SaxParameterObject context = new SaxParameterObject();
        final CommandTransition result = startParsing(context, "commandChainProcess.xml");

        @SuppressWarnings("unchecked")
        final List<Action> actions = (List<Action>) context.get(ACTION_LIST);
        final Transition transition = actions.get(0).getTransitions().get(0);
        assertThat(transition.getTarget(), CoreMatchers.is("Next"));
        assertThat(transition.getReturnValue(), CoreMatchers.is("OK"));
        assertThat(result, CoreMatchers.is(SUCCESS));
    }
}
