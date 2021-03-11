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

package org.mwolff.command.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Parameter;

public class MockitoExtension implements TestInstancePostProcessor, ParameterResolver {

    @Override
    public void postProcessTestInstance(final Object testInstance, final ExtensionContext context) {
        MockitoAnnotations.initMocks(testInstance);
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        return parameterContext.getParameter().isAnnotationPresent(Mock.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        return getMock(parameterContext.getParameter(), extensionContext);
    }

    private Object getMock(final Parameter parameter, final ExtensionContext extensionContext) {
        final Class<?> mockType = parameter.getType();
        final Store mocks = extensionContext.getStore(Namespace.create(MockitoExtension.class, mockType));
        final String mockName = getMockName(parameter);

        if (mockName != null) {
            return mocks.getOrComputeIfAbsent(mockName, key -> Mockito.mock(mockType, mockName));
        } else {
            return mocks.getOrComputeIfAbsent(mockType.getCanonicalName(), key -> Mockito.mock(mockType));
        }
    }

    private String getMockName(final Parameter parameter) {
        final String explicitMockName = parameter.getAnnotation(Mock.class).name().trim();
        if (!explicitMockName.isEmpty()) {
            return explicitMockName;
        } else if (parameter.isNamePresent()) {
            return parameter.getName();
        }
        return null;
    }

}
