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
package de.mwolff.commons.command.samplecommands;

import de.mwolff.commons.command.DefaultParameterObject;
import de.mwolff.commons.command.GenericParameterObject;
import de.mwolff.commons.command.iface.ChainCommand;
import de.mwolff.commons.command.iface.ProcessCommand;

public class PriorityTwoTestCommand<T extends GenericParameterObject> implements ChainCommand<T>, ProcessCommand<T> {

    @Override
    public void execute(T context) {
        if (context != DefaultParameterObject.NULLCONTEXT) {
            context.put("PriorityTwoTestCommand", "PriorityTwoTestCommand");
            String priorString = context.getAsString("priority");
            if ("NullObject".equals(priorString)) {
                priorString = "";
            }
            priorString += "2-";
            context.put("priority", priorString);
        }
    }

    @Override
    public boolean executeAsChain(T context) {
        String priorString = context.getAsString("priority");
        if ("NullObject".equals(priorString)) {
            priorString = "";
        }
        priorString += "B-";
        context.put("priority", priorString);
        return true;
    }

    @Override
    public String executeAsProcess(String startCommand, T context) {
        return null;
    }

    @Override
    public String getProcessID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProcessID(String processID) {
        // TODO Auto-generated method stub

    }

}
