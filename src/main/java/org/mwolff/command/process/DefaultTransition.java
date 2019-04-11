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
package org.mwolff.command.process;

import java.util.Optional;

public class DefaultTransition implements Transition {

    private String returnValue;
    private String target;

    @Override
    public String getReturnValue() {
        return returnValue;
    }

    @Override
    public Optional<String> getTarget() {
        return Optional.ofNullable(target);
    }

    @Override
    public void setReturnValue(final String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void setTarget(final String target) {
        this.target = target;
    }

}
