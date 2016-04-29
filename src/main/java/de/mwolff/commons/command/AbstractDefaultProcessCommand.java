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
package de.mwolff.commons.command;

import java.util.ArrayList;
import java.util.List;

import de.mwolff.commons.command.iface.ParameterObject;
import de.mwolff.commons.command.iface.ProcessCommand;
import de.mwolff.commons.command.iface.Transition;

/**
 * Default implementation for a chain-command. You may use
 * <code>executeAsChain</code> for all executions of the <code>command</code> or
 * <code>commandContainer</code>.
 */
public abstract class AbstractDefaultProcessCommand<T extends ParameterObject> implements ProcessCommand<T> {

    protected String processID;
    protected List<Transition> transitionList = new ArrayList<Transition>();

    /** Default constructor */
    public AbstractDefaultProcessCommand() {
        super();
    }

    /** Constructor with process ID */
    public AbstractDefaultProcessCommand(String processID) {
        this.processID = processID;
    }

    @Override
    public void addTransition(final Transition transition) {
        transitionList.add(transition);
    }

    /**
     * @see de.mwolff.commons.command.iface.Command#executeAsProcess(de.mwolff.commons.command.iface.ParameterObject)
     */
    @Override
    public abstract String executeAsProcess(String startCommand, T context);

    @Override
    public String findNext(final String next) {
        for (final Transition transition : transitionList) {
            if (next.equals(transition.getReturnValue())) {
                return transition.getTarget();
            }
        }
        return null;
    }

    @Override
    /**
     * @see de.mwolff.commons.command.Command#getProcessID()
     */
    public String getProcessID() {
        return this.processID;
    }

    @Override
    public List<Transition> getTransitionList() {
        return new ArrayList<Transition>(transitionList);
    }

    @Override
    /**
     * @see de.mwolff.commons.command.Command#getProcessID()
     */
    public void setProcessID(final String processID) {
        this.processID = processID;
    }

}
