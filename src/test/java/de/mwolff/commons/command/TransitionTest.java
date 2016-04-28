package de.mwolff.commons.command;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import de.mwolff.commons.command.iface.Transition;

public class TransitionTest {

    @Test
    public void transition() throws Exception {
        Transition transition = new DefaultTransition();
        transition.setReturnValue("OK");
        transition.setTarget("Next");
        assertThat("OK", is(transition.getReturnValue()));
        assertThat("Next", is(transition.getTarget()));
    }

}
