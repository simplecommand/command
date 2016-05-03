package de.mwolff.commons.command;

import de.mwolff.commons.command.iface.Transition;

public class DefaultTransition implements Transition {

    private String returnValue;
    private String target;

    @Override
    public String getReturnValue() {
        return returnValue;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public void setReturnValue(final String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void setTarget(final String target) {
        this.target = target;
    }

}
