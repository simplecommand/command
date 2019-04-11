/** Simple Command Framework.
 * 
 * Framework for easy building software that fits the SOLID principles.
 * 
 * @author Manfred Wolff <m.wolff@neusta.de>
 * 
 *         Download:
 *         https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git
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

package org.mwolff.command;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.DefaultTransition;
import org.mwolff.command.process.ProcessCommand;
import org.mwolff.command.process.Transition;
import org.mwolff.command.samplecommands.ProcessTestCommandStart;

import java.util.Optional;

public class ProcessCommandTest {

    @Test
    public void executeAsProcessSimpleTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<>(
                "Start");
        final GenericParameterObject context = new DefaultParameterObject();
        final String result = processTestStartCommand.executeAsProcess(context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - ", processflow);
        Assert.assertEquals("OK", result);
    }

    @Test
    public void getProcessNameTest() throws Exception {
        final ProcessTestCommandStart<GenericParameterObject> processTestStartCommand = new ProcessTestCommandStart<>(
                "Start");
        final String result = processTestStartCommand.getProcessID();
        Assert.assertEquals("Start", result);
    }

    @Test
    public void processANullContainer() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final DefaultCommandContainer<GenericParameterObject> container = new DefaultCommandContainer<>();
        String result = container.executeAsProcess("Start", context);
        Assert.assertNull(result);
        result = container.getProcessID();
        Assert.assertNull(result);
    }

    @Test
    public void testDefaultMethods() throws Exception {
        final ProcessCommand<Object> pc = new ProcessCommand<Object>() {

            @Override
            public String getProcessID() {
                return "getProcessID";
            }

            @Override
            public void setProcessID(final String processID) {
            }

            @Override
            public String executeAsProcess(String startCommand, Object context) {
                return null;
            }

            @Override
            public String executeAsProcess(Object context) {
                return null;
            }

            @Override
            public CommandTransition executeCommand(Object parameterObject) {
                return null;
            }

            @Override
            public CommandTransition executeCommandAsChain(Object parameterObject) {
                return null;
            }

        };
        Assert.assertNull(pc.executeAsProcess("START", DefaultParameterObject.NULLCONTEXT));
        Assert.assertNull(pc.executeAsProcess(DefaultParameterObject.NULLCONTEXT));
        Assert.assertThat(pc.getProcessID(), CoreMatchers.is("getProcessID"));
        Assert.assertThat(pc.getTransitionList(), CoreMatchers.notNullValue());
        Assert.assertThat(pc.findNext("Hello"), CoreMatchers.is("Hello"));
        final Transition transition = new DefaultTransition();
        pc.addTransition(transition);
        pc.setProcessID("");
        pc.executeCommand(null);
        pc.executeCommandAsChain(null);
    }
}
