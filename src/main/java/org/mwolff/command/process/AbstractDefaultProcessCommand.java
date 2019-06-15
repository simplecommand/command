/*        Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
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
 *         02110-1301 USA
 */
package org.mwolff.command.process;

import org.mwolff.command.chain.AbstractDefaultChainCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Default implementation for a process-command. You may use  <code>executeAsProcess</code> for all executions of the
 * <code>command</code> or  <code>commandContainer</code>.
 */
public abstract class AbstractDefaultProcessCommand<T extends Object> extends AbstractDefaultChainCommand<T>
        implements ProcessCommand<T> {

    protected String           processID;
    protected List<Transition> transitionList = new ArrayList<>();

    public AbstractDefaultProcessCommand() {
        super();
    }

    public AbstractDefaultProcessCommand(final String processID) {
        this.processID = processID;
    }

    @Override
    public void addTransition(final Transition transition) {
        transitionList.add(transition);
    }

    @Override
    public Optional<String> findNext(final String next) {
        for (final Transition transition : transitionList) {
            if (next.equals(transition.getReturnValue())) {
                return transition.getTarget();
            }
        }
        return Optional.empty();
    }

    @Override
    public String getProcessID() {
        return this.processID;
    }

    @Override
    public List<Transition> getTransitionList() {
        return new ArrayList<>(transitionList);
    }

    @Override
    public void setProcessID(final String processID) {
        this.processID = processID;
    }

    @Override
    public Optional<String> executeAsProcess(String startCommand, T context) {
        return Optional.empty();
    }
}
