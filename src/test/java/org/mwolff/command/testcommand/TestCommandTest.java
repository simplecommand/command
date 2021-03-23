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

package org.mwolff.command.testcommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwolff.command.interfaces.CommandTransition;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mwolff.command.interfaces.CommandTransition.*;

public class TestCommandTest {

    private GenericParameterObject defaultParameterObject;

    @BeforeEach
    public void setUp() {
        defaultParameterObject = DefaultParameterObject.getInstance();
    }
    
    @Test
    public void testWritePattern() throws Exception {
        // given
        TestCommand testCommand = new TestCommand("1-", null);
        
        // when
        CommandTransition result = testCommand.executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-"));
        assertThat(result, is(SUCCESS));
    }

    private String getResult() {
        String strResult = defaultParameterObject.getAsString("resultString");
        return strResult;
    }        

       
    @Test
    public void testTwoInARow() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(new TestCommand("1-", null))
                                                          .addCommand(new TestCommand("2-", null))
                                                           .executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-2-"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testThreeInARow() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(new TestCommand("1-", null))
                                                          .addCommand(new TestCommand("2-", null))
                                                          .addCommand(new TestCommand("3-", null))
                                                           .executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-2-3-"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testThreeInARowPriority() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(3, new TestCommand("1-", null))
                                                          .addCommand(1, new TestCommand("2-", null))
                                                          .addCommand(2, new TestCommand("3-", null))
                                                          .executeCommand(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("2-3-1-"));
        assertThat(result, is(SUCCESS));
    }

    @Test
    public void testThreeInARowAsChainToBreaks() throws Exception {
        // given
        
        // when
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        CommandTransition result = defaultCommandContainer.addCommand(new TestCommand("1-", NEXT))
                                                          .addCommand(new TestCommand("2-", DONE))
                                                          .addCommand(new TestCommand("3-", NEXT))
                                                          .executeCommandAsChain(defaultParameterObject);
        String strResult = getResult();
        
        // then
        assertThat(strResult, is("1-2-"));
        assertThat(result, is(DONE));
    }
}
