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

package org.mwolff.command.samplecommands;

import org.apache.log4j.Logger;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.AbstractDefaultProcessCommand;

public class ProcessTestCommandEnd<T extends GenericParameterObject> extends AbstractDefaultProcessCommand<T> {

    private static final Logger LOG = Logger.getLogger(ProcessTestCommandEnd.class);

    public ProcessTestCommandEnd() {
        super();
    }

    public ProcessTestCommandEnd(final String processID) {
        super(processID);
    }

    @Override
    public CommandTransition executeCommand(T context) {
        String result = context.getAsString("result");
        if ((result == null) || ("NullObject".equals(result))) {
            result = "";
        }
        result += processID + " - ";
        context.put("result", result);
        return CommandTransition.SUCCESS;
    }

    @Override
    public String executeAsProcess(final T context) {
        executeCommand(context);
        return null;
    }

    @Override
    public boolean executeAsChain(T parameterObject) {
        ProcessTestCommandEnd.LOG.error("nothing to do");
        return true;
    }
}
