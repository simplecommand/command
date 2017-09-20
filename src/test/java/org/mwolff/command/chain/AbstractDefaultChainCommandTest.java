/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>

    Download: https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git

    Copyright (C) 2018 Manfred Wolff and the simple command community

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
package org.mwolff.command.chain;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.Command;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.samplecommands.SimpleTestCommand;

public class AbstractDefaultChainCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("deprecation")
    @Test
    public void testExecute() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        command.execute(context);
        Assert.assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testExecuteNull() throws Exception {
        thrown.expect(CommandException.class);
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        command.execute(null);
    }

    @SuppressWarnings("deprecation")
    public void testExecuteAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
        final boolean result = command.executeAsChain(context);
        Assert.assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
        Assert.assertThat(result, CoreMatchers.is(Boolean.TRUE));
    }

    @SuppressWarnings("deprecation")
    public void testExecuteAsChainNull() throws Exception {
        final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
        final boolean result = command.executeAsChain(null);
        Assert.assertThat(result, CoreMatchers.is(Boolean.FALSE));
    }

    @Test
    public void testExecuteCommand() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        final CommandTransition result = command.executeCommand(context);
        Assert.assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.SUCCESS));
    }

    @Test
    public void testExecuteCommandNull() throws Exception {
        final Command<GenericParameterObject> command = new SimpleTestCommand<>();
        final CommandTransition result = command.executeCommand(null);
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.FAILURE));
    }

    @Test
    public void testExecuteCommandAsChain() throws Exception {
        final GenericParameterObject context = new DefaultParameterObject();
        final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
        final CommandTransition result = command.executeCommandAsChain(context);
        Assert.assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
        Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.NEXT));
    }

    @Test
    public void testExecuteCommandAsChainNull() throws Exception {
        final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
        final CommandTransition result = command.executeCommandAsChain(null);
        Assert.assertThat(result, CoreMatchers.is(CommandTransition.DONE));
    }

}
