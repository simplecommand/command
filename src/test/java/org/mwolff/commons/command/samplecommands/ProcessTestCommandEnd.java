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
package org.mwolff.commons.command.samplecommands;


import org.apache.log4j.Logger;
import org.mwolff.commons.command.AbstractDefaultProcessCommand;
import org.mwolff.commons.command.GenericParameterObject;
import org.mwolff.commons.command.iface.CommandException;

public class ProcessTestCommandEnd<T extends GenericParameterObject> extends AbstractDefaultProcessCommand<T> {

    private static final Logger LOG = Logger.getLogger(ProcessTestCommandEnd.class);

    public ProcessTestCommandEnd() {
        super();
    }

    public ProcessTestCommandEnd(final String processID) {
        super(processID);
    }

    @Override
    public void execute(final T context) throws CommandException {
        String result = context.getAsString("result");
        result += processID + " - ";
        context.put("result", result);
    }

    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        try {
            execute(context);
        } catch (final CommandException e) {
            LOG.error(e);
        }
        return null;
    }
    
    @Override
    public void executeOnly(T context) {
    }

}
