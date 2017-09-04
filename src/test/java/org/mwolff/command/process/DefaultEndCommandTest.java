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
package org.mwolff.command.process;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class DefaultEndCommandTest {

    private DefaultEndCommand defaultEndCommand;

    @Before
    public void setUp() {
        defaultEndCommand = new DefaultEndCommand();
    }
    
    @Test
    public void testExecuteAsProcessSimple() throws Exception {
        assertThat(defaultEndCommand.executeAsProcess(null), is("END"));
    }

    @Test
    public void testEndCommandInProcessChain() throws Exception {
        DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
        defaultEndCommand.setProcessID("END");
        DefaultParameterObject context = new DefaultParameterObject();
        defaultCommandContainer.addCommand(defaultEndCommand);
        assertThat(defaultCommandContainer.executeAsProcess("END", context), is("END"));
    }
    
    @Test
    public void testExecuteAsProcessComplex() throws Exception {
        assertThat(defaultEndCommand.executeAsProcess("START", null), is("END"));
    }
    
}
