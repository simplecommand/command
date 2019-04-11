/*         Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
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
 *         02110-1301 USA
 */
package org.mwolff.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import java.util.Optional;

public class ProcessTestCommandNextTest {

    @Test
    void executeAsProcessNull( ) {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        Optional<String> result = command.executeAsProcess(context);
        assertThat(result.get(), is("OK"));
    }

    @Test
    void executeAsProcessNotNull( ) {
        GenericParameterObject context = DefaultParameterObject.getInstance();
        context.put("counter", Integer.valueOf(1));
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        Optional<String> result = command.executeAsProcess(context);
        assertThat(result, is(Optional.empty()));
    }
    
    @Test
    void executeCommand( ) {
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        CommandTransition result = command.executeCommand(null);
        assertThat(result, is(CommandTransition.SUCCESS));
        
    }
    
    @Test
    void getProceddID() {
        ProcessTestCommandNext<GenericParameterObject> command = new ProcessTestCommandNext<>();
        String result = command.getProcessID();
        assertThat(result, nullValue());
    }
}
