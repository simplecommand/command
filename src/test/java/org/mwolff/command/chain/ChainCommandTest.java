package org.mwolff.command.chain;

import static org.mwolff.command.CommandTransition.NEXT;
import static org.mwolff.command.CommandTransition.SUCCESS;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class ChainCommandTest {

   @Test
   public void testInterfaceDefaultExecuteCommandAsChain() {

      final GenericParameterObject context = new DefaultParameterObject();
      final ChainCommand<GenericParameterObject> command = new AbstractDefaultChainCommand<GenericParameterObject>() {

         @Override
         public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
            return SUCCESS;
         }

      };

      CommandTransition transition = command.executeCommand(context);
      Assert.assertThat(transition, CoreMatchers.is(SUCCESS));
      transition = command.executeCommandAsChain(context);
      Assert.assertThat(transition, CoreMatchers.is(NEXT));
   }

   @Test
   public void testInterfaceDefaultExecuteCommandAsChainFAILED() {

      final ChainCommand<GenericParameterObject> command = new AbstractDefaultChainCommand<GenericParameterObject>() {

         @Override
         public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
            return SUCCESS;
         }

      };

      CommandTransition transition = command.executeCommand(null);
      Assert.assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
      transition = command.executeCommandAsChain(null);
      Assert.assertThat(transition, CoreMatchers.is(CommandTransition.NEXT));
   }

}
