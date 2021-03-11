/** Simple Command Framework.
 *
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Download:
 *         https://github.com/simplecommand/command.git
 *
 *         Copyright (C) 2018-2021 Manfred Wolff and the simple command community
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
 *         02110-1301
 *         USA */

package org.mwolff.command.sax;

import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class SaxParameterObject implements GenericParameterObject {

    GenericParameterObject context = DefaultParameterObject.getInstance();

    @Override
    public Object get(String key) {
        return context.get(key);
    }

    public Object get(GlobalCommandConstants key) {
        return context.get(key.toString());
    }

    @Override
    public String getAsString(String key) {
        return context.getAsString(key);
    }

    public String getAsString(GlobalCommandConstants key) {
        return context.getAsString(key.toString());
    }

    @Override
    public void put(String key, Object value) {
        context.put(key, value);
    }

    public void put(GlobalCommandConstants key, Object value) {
        context.put(key.toString(), value);
    }

}
