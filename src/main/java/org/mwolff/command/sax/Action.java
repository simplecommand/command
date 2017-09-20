package org.mwolff.command.sax;

import java.util.ArrayList;
import java.util.List;

import org.mwolff.command.process.Transition;

public class Action {

    private String                 classname;
    private String                 id;
    private final List<Transition> transitions = new ArrayList<>();

    public void setTransition(final Transition transition) {
        transitions.add(transition);
    }

    public List<Transition> getTransitions() {
        return new ArrayList<>(transitions);
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(final String classname) {
        this.classname = classname;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

}
