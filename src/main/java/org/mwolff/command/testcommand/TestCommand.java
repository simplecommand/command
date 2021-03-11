package org.mwolff.command.testcommand;

import org.mwolff.command.CommandTransition;
import org.mwolff.command.chain.AbstractDefaultChainCommand;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.mwolff.command.CommandTransition.SUCCESS;

public class TestCommand extends AbstractDefaultChainCommand<GenericParameterObject> {

   private String pattern;
   CommandTransition chainResult;

   public TestCommand(final String pattern, final CommandTransition result) {
      this.pattern = pattern;
      chainResult = result;
   }

   @Override
   public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
      String result = parameterObject.getAsString("resultString");
      result += pattern;
      parameterObject.put("resultString", result);
      return SUCCESS;
   }

   @Override
   public CommandTransition executeCommandAsChain(final GenericParameterObject parameterObject) {
      executeCommand(parameterObject);
      return chainResult;
   }

}
