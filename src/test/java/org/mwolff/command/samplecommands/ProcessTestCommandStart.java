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
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.process.AbstractDefaultProcessCommand;

public class ProcessTestCommandStart<T extends GenericParameterObject> extends AbstractDefaultProcessCommand<T> {

    private static final Logger LOG = Logger.getLogger(ProcessTestCommandStart.class);

    public ProcessTestCommandStart() {
        super();
    }

    public ProcessTestCommandStart(final String processID) {
        super(processID);
    }

    @Override
    public void execute(final T context) throws CommandException {
        String result = context.getAsString("result");
        if (result.equals("NullObject")) {
            result = "";
        }
        result += processID + " - ";
        context.put("result", result);
    }

    @Override
    public String executeAsProcess(final T context) {
        try {
            execute(context);
        } catch (final CommandException e) {
            ProcessTestCommandStart.LOG.error(e);
        }
        return "OK";
    }

     @Override
    public boolean executeAsChain(T parameterObject) {
        LOG.error("nothing to do");
        return false;
    }

    @Override
    public String executeAsProcess(String startCommand, T context) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandTransition executeCommand(T parameterObject) {
        try {
            execute(parameterObject);
        } catch (CommandException e) {
            return CommandTransition.FAILURE;
        }
        return CommandTransition.SUCCESS;
    }

    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        boolean result = executeAsChain(parameterObject);
        return (result == true) ? CommandTransition.SUCCESS : CommandTransition.ABORT;
    }

}
