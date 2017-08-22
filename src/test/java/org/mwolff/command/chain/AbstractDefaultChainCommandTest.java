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
package org.mwolff.command.chain;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mwolff.command.CommandException;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.commons.command.samplecommands.ExceptionCommand;
import org.mwolff.commons.command.samplecommands.SimpleTestCommand;

public class AbstractDefaultChainCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void executeAsChainReturnsFalse() throws Exception {
        final ExceptionCommand<GenericParameterObject> defaultCommand = new ExceptionCommand<>();
        Assert.assertThat(defaultCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT), Matchers.is(false));
    }

    @Test
    public void executeAsChainReturnsTrue() throws Exception {
        final SimpleTestCommand<GenericParameterObject> simpleTestCommand = new SimpleTestCommand<>();
        Assert.assertThat(simpleTestCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT), Matchers.is(true));
    }

    @Test
    public void executeTest() throws Exception {
        final ExceptionCommand<GenericParameterObject> exceptionCommand = new ExceptionCommand<>();
        final GenericParameterObject defaultContext = new DefaultParameterObject();
        thrown.expect(CommandException.class);
        thrown.expectMessage("Method is not implemented yet.");
        exceptionCommand.execute(defaultContext);
    }
    
    @Test
    public void executeOnlyTest() throws Exception {
        final ExceptionCommand<GenericParameterObject> exceptionCommand = new ExceptionCommand<>();
        final GenericParameterObject defaultContext = new DefaultParameterObject();
        exceptionCommand.executeOnly(defaultContext);
    }

}
