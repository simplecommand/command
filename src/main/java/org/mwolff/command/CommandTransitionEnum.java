package org.mwolff.command;

public interface CommandTransitionEnum {

    /**
     * Transition for an execute Method. 
     * 
     * State can be:
     *  
     * SUCCESS : Execution was Successfully done. The next command can overtake. 
     * DONE    : For CoR: The work is successfully done, the chain can be aborted. 
     * FAILURE : An fatal error has occurred. The chain should be aborted
     * 
     * @since 1.5.0
     */
    public enum CommandTransition {
        SUCCESS, FAILURE, DONE
    }

}
