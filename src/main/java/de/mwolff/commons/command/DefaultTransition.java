package de.mwolff.commons.command;

import de.mwolff.commons.command.iface.Transition;

public class DefaultTransition implements Transition {
    
    private String returnValue;
    private String target;

    @Override
    public String getReturnValue() {
        return this.returnValue;
    }

    @Override
    public String getTarget() {
        return this.target;
    }

    @Override
    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void setTarget(String target) {
        this.target = target;
    }

}
