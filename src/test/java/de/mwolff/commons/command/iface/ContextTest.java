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
package de.mwolff.commons.command.iface;

import org.junit.Assert;
import org.junit.Test;

import de.mwolff.commons.command.DefaultContext;
import de.mwolff.commons.command.GenericContext;

public class ContextTest {

    private static final String  INTEGER_VALUE = "IntegerValue";
    private static final String  STRING_VALUE  = "StringValue";

    private final GenericContext context       = new DefaultContext();

    @Test
    public void testContextInterface() throws Exception {
        Assert.assertNotNull(context);
    }

    @Test
    public void testPutGetContext() throws Exception {
        context.put(ContextTest.STRING_VALUE, ContextTest.STRING_VALUE);
        final String stringValue = (String) context.get(ContextTest.STRING_VALUE);
        Assert.assertEquals(ContextTest.STRING_VALUE, stringValue);
    }

    @Test
    public void testGet() throws Exception {
        context.put(ContextTest.INTEGER_VALUE, Integer.valueOf(42));
        final Integer integerValue = (Integer) context.get(ContextTest.INTEGER_VALUE);
        Assert.assertEquals(Integer.valueOf(42), integerValue);
    }

    @Test
    public void testGetAsString() throws Exception {
        context.put(ContextTest.STRING_VALUE, ContextTest.STRING_VALUE);
        final String stringValue = context.getAsString(ContextTest.STRING_VALUE);
        Assert.assertEquals(ContextTest.STRING_VALUE, stringValue);
    }

    @Test
    public void testNullContext() throws Exception {
        final GenericContext nullContext = DefaultContext.NULLCONTEXT;
        nullContext.put("myKey", "myValue");
        Assert.assertEquals("myValue", nullContext.getAsString("myKey"));
    }

    @Test
    public void testNullValue() throws Exception {
        final Object value = context.getAsString("null");
        Assert.assertNotNull(value);
    }
}
