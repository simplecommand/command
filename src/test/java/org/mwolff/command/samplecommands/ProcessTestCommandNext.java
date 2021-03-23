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

package org.mwolff.command.samplecommands;

import org.mwolff.command.interfaces.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.AbstractDefaultProcessCommand;

public class ProcessTestCommandNext<T extends GenericParameterObject> extends AbstractDefaultProcessCommand<T> {

    public ProcessTestCommandNext() {
        super();
    }

    public ProcessTestCommandNext(final String processID) {
        super(processID);
    }

    @Override
    public String executeAsProcess(final T context) {
        executeCommand(context);
        // Decision: The first time we redirect to Start, otherwise we end it.
        Integer counter = (Integer) context.get("counter");
        if (counter == null) {
            counter = Integer.valueOf(1);
            context.put("counter", counter);
            return "OK";
        }
        return "";
    }

    @Override
    public String getProcessID() {
        return super.getProcessID();
    }

    @Override
    public CommandTransition executeCommand(T parameterObject) {
        return CommandTransition.SUCCESS;
    }
}
