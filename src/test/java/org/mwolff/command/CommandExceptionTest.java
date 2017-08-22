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
package org.mwolff.command;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mwolff.command.CommandException;

public class CommandExceptionTest {

    @Test
    public void commandExceptionDefaultConstructorTest() throws Exception {
        Assert.assertThat(new CommandException(), Matchers.notNullValue());
    }

    @Test
    public void commandExceptionWithMessageAndThrowableTest() throws Exception {
        final CommandException commandException = new CommandException("message", null);
        Assert.assertThat(commandException.getMessage(), Matchers.is("message"));
        Assert.assertThat(commandException.getCause(), Matchers.nullValue());
    }

    @Test
    public void commandExceptionWithMessageTest() throws Exception {
        final CommandException commandException = new CommandException("message");
        Assert.assertThat(commandException.getMessage(), Matchers.is("message"));
    }

    @Test
    public void commandExceptionWithThrowableTest() throws Exception {
        final Exception exception = new Exception();
        final CommandException commandException = new CommandException(exception);
        Assert.assertThat(commandException.getCause(), Matchers.is(exception));
    }

}
