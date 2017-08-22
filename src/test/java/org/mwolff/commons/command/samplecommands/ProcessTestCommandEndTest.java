package org.mwolff.commons.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mwolff.command.chain.XMLChainBuilder;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.ParameterObject;

public class ProcessTestCommandEndTest {

        @Test
    public void testEndCommand() throws Exception {
        final XMLChainBuilder<ParameterObject> xmlChainBuilder = new XMLChainBuilder<>(
                "/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("END", context);
        assertNull(result);
        String contextResult = context.getAsString("result");
        assertThat(contextResult, is("END - "));

    }
}
