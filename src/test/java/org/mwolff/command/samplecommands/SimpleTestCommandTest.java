package org.mwolff.command.samplecommands;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mwolff.command.Command;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class SimpleTestCommandTest {

   @Test
   public void testExecuteCommand() throws Exception {
      final GenericParameterObject context = new DefaultParameterObject();
      final Command<GenericParameterObject> command = new SimpleTestCommand<>();
      command.executeCommand(context);
      Assert.assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
      Assert.assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
   }
}
