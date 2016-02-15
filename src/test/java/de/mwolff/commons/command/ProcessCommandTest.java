/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>
    Download: https://github.com/simplecommand/SimpleCommandFramework


    Copyright (C) 2015 neusta software development

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
    USA
 */
package de.mwolff.commons.command;

import org.junit.Assert;
import org.junit.Test;

import de.mwolff.commons.command.samplecommands.ProcessTestCommandEnd;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandNext;
import de.mwolff.commons.command.samplecommands.ProcessTestCommandStart;

public class ProcessCommandTest {

    @Test
    public void executeAsProcessSimpleTest() throws Exception {
        final ProcessTestCommandStart<GenericContext> processTestStartCommand = new ProcessTestCommandStart<GenericContext>(
                "Start");
        final GenericContext context = new DefaultContext();
        final String result = processTestStartCommand.executeAsProcess("", context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - ", processflow);
        Assert.assertEquals("Next", result);
    }

    @Test
    public void getProcessNameTest() throws Exception {
        final ProcessTestCommandStart<GenericContext> processTestStartCommand = new ProcessTestCommandStart<GenericContext>(
                "Start");
        final String result = processTestStartCommand.getProcessID();
        Assert.assertEquals("Start", result);
    }

    @Test
    public void executeTwoSimpleProcessesInARow() throws Exception {
        final GenericContext context = new DefaultContext();
        final ProcessTestCommandStart<GenericContext> processTestStartCommand = new ProcessTestCommandStart<GenericContext>(
                "Start");
        final ProcessTestCommandEnd<GenericContext> processTestEndCommand = new ProcessTestCommandEnd<GenericContext>(
                "Next");
        final DefaultCommandContainer<GenericContext> container = new DefaultCommandContainer<GenericContext>();
        container.addCommand(processTestStartCommand);
        container.addCommand(processTestEndCommand);
        container.executeAsProcess("Start", context);
        final String processflow = context.getAsString("result");
        Assert.assertEquals("Start - Next - ", processflow);
    }

    @Test
    public void processANullContainer() throws Exception {
        final GenericContext context = new DefaultContext();
        final DefaultCommandContainer<GenericContext> container = new DefaultCommandContainer<GenericContext>();
        String result = container.executeAsProcess("Start", context);
        Assert.assertNull(result);
        result = container.getProcessID();
        Assert.assertNull(result);
    }

    @Test
    public void processMoreCompicated() throws Exception {
        final DefaultCommandContainer<GenericContext> container = new DefaultCommandContainer<GenericContext>();
        container.addCommand(new ProcessTestCommandStart<GenericContext>("Start"));
        container.addCommand(new ProcessTestCommandNext<GenericContext>("Next"));
        GenericContext context = new DefaultContext();
        String result = container.executeAsProcess("Start", context);
        final String processflow = context.getAsString("result");
        Assert.assertNull(result);
        Assert.assertEquals("Start - Next - Start - Next - ", processflow);

    }

}
