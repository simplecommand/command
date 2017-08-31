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

package org.mwolff.commons.command.samplecommands;

import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.ProcessCommand;

public class PriorityThreeTestCommand<T extends GenericParameterObject> implements ChainCommand<T>, ProcessCommand<T> {

    @Override
    public void execute(final T context) {
        context.put("PriorityThreeTestCommand", "PriorityThreeTestCommand");
        String priorString = context.getAsString("priority");
        if ("NullObject".equals(priorString)) {
            priorString = "";
        }
        priorString += "3-";
        context.put("priority", priorString);
    }

    @Override
    public boolean executeAsChain(final T context) {
        String priorString = context.getAsString("priority");
        priorString += "C-";
        context.put("priority", priorString);
        return false;
    }

    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        return null;
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @Override
    public void setProcessID(final String processID) {
    }
}
