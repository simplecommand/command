package org.mwolff.command.testcommand;

import static org.mwolff.command.CommandTransition.*;

import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransition;
import org.mwolff.command.chain.AbstractDefaultChainCommand;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class TestCommand extends AbstractDefaultChainCommand<GenericParameterObject>{

    private String pattern;
    CommandTransition chainResult;
    
    public TestCommand(String pattern, CommandTransition result) {
        this.pattern = pattern;
        this.chainResult = result;
    }

    @Override
    public CommandTransition executeCommand(GenericParameterObject parameterObject) {
        String result = parameterObject.getAsString("resultString");
        result += pattern;
        parameterObject.put("resultString", result);
        return SUCCESS;
    }

    @Override
    public CommandTransition executeCommandAsChain(GenericParameterObject parameterObject) {
        executeCommand(parameterObject);
        return chainResult;
    }

}
