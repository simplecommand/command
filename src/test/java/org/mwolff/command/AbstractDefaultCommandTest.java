package org.mwolff.command;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AbstractDefaultCommandTest {

   class MyTestClass extends AbstractDefaultCommand<String> {

      @Override
      public CommandTransition executeCommand(final String parameterObject) {
         return null;
      }

   }

   @Test
   void testExecute() throws Exception {
      final MyTestClass instance = new MyTestClass();
      Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
         instance.executeCommand(null);
      });
   }
}
