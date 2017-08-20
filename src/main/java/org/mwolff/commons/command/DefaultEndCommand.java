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
package org.mwolff.commons.command;

import org.apache.log4j.Logger;
import org.mwolff.commons.command.iface.CommandException;

public class DefaultEndCommand<T extends GenericParameterObject> extends AbstractDefaultProcessCommand<T> {

    private static final Logger LOG = Logger.getLogger(DefaultEndCommand.class);

    @Override
    public void execute(final T context) throws CommandException {
        DefaultEndCommand.LOG.error("nothing to do");
    }

    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        return "END";
    }

    @Override
    public String executeAsProcess(final T context) {
        return "END";
    }

    @Override
    public void executeOnly(T context) {
        super.executeOnly(context);
    }

    @Override
    public boolean executeAsChain(T parameterObject) {
        DefaultEndCommand.LOG.error("nothing to do");
        return false;
    }

}
