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
 */package de.mwolff.commons.command.iface;

//@formatter:off
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
//@formatter:on

import org.junit.Test;

import de.mwolff.commons.command.iface.CommandException;

public class CommandExceptionTest {
    
    @Test
    public void commandExceptionDefaultConstructorTest() throws Exception {
        assertThat(new CommandException(), notNullValue());
    }
    
    @Test
    public void commandExceptionWithMessageTest() throws Exception {
        CommandException commandException = new CommandException("message");
        assertThat(commandException.getMessage(), is("message"));
    }
    
    @Test
    public void commandExceptionWithMessageAndThrowableTest() throws Exception {
        CommandException commandException = new CommandException("message", null);
        assertThat(commandException.getMessage(), is("message"));
        assertThat(commandException.getCause(), nullValue());
    }

    @Test
    public void commandExceptionWithThrowableTest() throws Exception {
        Exception exception = new Exception();
        CommandException commandException = new CommandException(exception);
        assertThat(commandException.getCause(), is(exception));
    }

}
