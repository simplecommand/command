/*         Simple Command Framework.
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
package org.mwolff.command.sax;

import java.util.ArrayList;
import java.util.List;

import org.mwolff.command.process.Transition;

public class Action {

    private String                 classname;
    private String                 id;
    private final List<Transition> transitions = new ArrayList<>();

    public void setTransition(final Transition transition) {
        transitions.add(transition);
    }

    public List<Transition> getTransitions() {
        return new ArrayList<>(transitions);
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(final String classname) {
        this.classname = classname;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

}
