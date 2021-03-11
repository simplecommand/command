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

/** Simple context interface for pass values across commands. */
public interface GenericParameterObject {

    /** Returns the object of the given key.
     *
     * @param key
     *            The given key to return the value.
     * @return The value of the key */
    Object get(String key);

    /** Returns the object of the given key as String.
     *
     * @param errorString
     *            The given key to return a value as String.
     * @return The value of the key as string. */
    String getAsString(String errorString);

    /** Saves an object to the key.
     *
     * @param key
     *            the key.
     * @param value
     *            the key. */
    void put(String key, Object value);

}