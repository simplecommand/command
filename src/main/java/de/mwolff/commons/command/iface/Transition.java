package de.mwolff.commons.command.iface;

public interface Transition {
    
    String getReturnValue();
    
    String getTarget();
    
    void setReturnValue(final String returnValue);
    
    void setTarget(final String target);

}
