package org.mwolff.command;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;

public class AbstractDefaultCommandTest {

   class MyTestClass extends AbstractDefaultCommand<String> {

      @Override
      public CommandTransition executeCommand(final String parameterObject) {
         return null;
      }

   }

   @Test
   @DisplayName("The method call execute is possible because of API reasons, but should not be supported.")
   void testExecute() throws Exception {
      final MyTestClass instance = new MyTestClass();
      Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
         instance.execute(null);
      });
   }

}
