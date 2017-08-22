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
package org.mwolff.command.process;

import java.util.ArrayList;
import java.util.List;

import parameterobject.ParameterObject;

/**
 * Default implementation for a process-command. You may use
 * <code>executeAsProcess</code> for all executions of the <code>command</code> or
 * <code>commandContainer</code>.
 */
public abstract class AbstractDefaultProcessCommand<T extends ParameterObject> implements ProcessCommand<T> {

    protected String           processID;
    protected List<Transition> transitionList = new ArrayList<>();

    /** Default constructor */
    public AbstractDefaultProcessCommand() {
        super();
    }

    /** Constructor with process ID
     * @param processID The process id for this command.  
     */
    public AbstractDefaultProcessCommand(final String processID) {
        this.processID = processID;
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#addTransition(org.mwolff.commons.command.iface.Transition)
     */
    @Override
    public void addTransition(final Transition transition) {
        transitionList.add(transition);
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#findNext(java.lang.String)
     */
    @Override
    public String findNext(final String next) {
        for (final Transition transition : transitionList) {
            if (next.equals(transition.getReturnValue())) {
                return transition.getTarget();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#getProcessID()
     */
    @Override
    public String getProcessID() {
        return this.processID;
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#getTransitionList()
     */
    @Override
    public List<Transition> getTransitionList() {
        return new ArrayList<>(transitionList);
    }

    /*
     * (non-Javadoc)
     * @see org.mwolff.commons.command.iface.ProcessCommand#setProcessID(java.lang.String)
     */
    @Override
    public void setProcessID(final String processID) {
        this.processID = processID;
    }

}
