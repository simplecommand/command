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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParameterObjectTest {

    private static final String          INTEGER_VALUE = "IntegerValue";
    private static final String          STRING_VALUE  = "StringValue";

    private final GenericParameterObject context       = new DefaultParameterObject();

    @Test
    public void testContextInterface() throws Exception {
        assertNotNull(context);
    }

    @Test
    public void testGet() throws Exception {
        context.put(ParameterObjectTest.INTEGER_VALUE, Integer.valueOf(42));
        final Integer integerValue = (Integer) context.get(ParameterObjectTest.INTEGER_VALUE);
        assertEquals(Integer.valueOf(42), integerValue);
    }

    @Test
    public void testGetAsString() throws Exception {
        context.put(ParameterObjectTest.STRING_VALUE, ParameterObjectTest.STRING_VALUE);
        final String stringValue = context.getAsString(ParameterObjectTest.STRING_VALUE);
        assertEquals(ParameterObjectTest.STRING_VALUE, stringValue);
    }

    @Test
    public void testNullContext() throws Exception {
        final GenericParameterObject nullContext = new DefaultParameterObject();
        nullContext.put("myKey", "myValue");
        assertEquals("myValue", nullContext.getAsString("myKey"));
    }

    @Test
    public void testNullValue() throws Exception {
        final Object value = context.getAsString("null");
        assertNotNull(value);
    }

    @Test
    public void testPutGetContext() throws Exception {
        context.put(ParameterObjectTest.STRING_VALUE, ParameterObjectTest.STRING_VALUE);
        final String stringValue = (String) context.get(ParameterObjectTest.STRING_VALUE);
        assertEquals(ParameterObjectTest.STRING_VALUE, stringValue);
    }
}
