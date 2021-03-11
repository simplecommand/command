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
package org.mwolff.command.parameterobject;

import java.util.HashMap;
import java.util.Map;

/** A simple implementation of a generic context. */
public class DefaultParameterObject implements GenericParameterObject {

    /** A generic map storing the key/value pairs. */
    private final Map<String, Object>          genericMap  = new HashMap<>();

    /** Gets a fresh instance of an GenericParameterObject
     * 
     * @return The fresh instance */
    public static final GenericParameterObject getInstance() {
        return new DefaultParameterObject();
    }

    /** @see org.mwolff.command.parameterobject.GenericParameterObject#get(java.lang.String) */
    @Override
    public Object get(final String key) {
        return genericMap.get(key);
    }

    /** @see org.mwolff.command.parameterobject.GenericParameterObject#getAsString(java.lang.String) */
    @Override
    public String getAsString(final String key) {
        Object object = genericMap.get(key);
        if (object == null) {
            object = "";
        }
        return object.toString();
    }

    /** @see org.mwolff.command.parameterobject.GenericParameterObject#put(java.lang.String,
     *      java.lang.Object) */
    @Override
    public void put(final String key, final Object value) {
        genericMap.put(key, value);
    }

    public static GenericParameterObject getStandardContext() {
        return new DefaultParameterObject();
    }

    public static GenericParameterObject getStandardContext(final String key, final Object value) {
        final DefaultParameterObject defaultParameterObject = new DefaultParameterObject();
        defaultParameterObject.put(key, value);
        return defaultParameterObject;
    }
}
