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

import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.chain.AbstractDefaultChainCommand;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class AbortTestCommand<T extends GenericParameterObject> extends AbstractDefaultChainCommand<T>
       {

    /*
     * @see de.mwolff.commons.command.Command#execute()
     */
    @Override
    public void execute(final T context) throws CommandException{
        context.put("status", "proceeded");
        throw new CommandException("ABORT.");
    }

    @Override
    public boolean executeAsChain(T context) {
        try {
            execute(context);
        } catch (CommandException e) {
            // Only for test porpuse
        }
        return false;
    }
    
    @Override
    public CommandTransition executeCommand(T parameterObject) {
        try {
            execute(parameterObject);
        } catch (CommandException e) {
            // Only for test porpuse
        }
        return CommandTransition.ABORT;
    }

    @Override
    public CommandTransition executeCommandAsChain(T parameterObject) {
        boolean result = executeAsChain(parameterObject);
        return (result == true) ? CommandTransition.SUCCESS : CommandTransition.ABORT;
    }

}
