package org.mwolff.command.extensions;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Parameter;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
      Class<?> mockType = parameter.getType();
      Store mocks = extensionContext.getStore(Namespace.create(MockitoExtension.class, mockType));
      String mockName = getMockName(parameter);

      if (mockName != null) {
         return mocks.getOrComputeIfAbsent(mockName, key -> mock(mockType, mockName));
      } else {
         return mocks.getOrComputeIfAbsent(mockType.getCanonicalName(), key -> mock(mockType));
      }
   }

   private String getMockName(final Parameter parameter) {
      String explicitMockName = parameter.getAnnotation(Mock.class).name().trim();
      if (!explicitMockName.isEmpty()) {
         return explicitMockName;
      } else if (parameter.isNamePresent()) {
         return parameter.getName();
      }
      return null;
   }

}
