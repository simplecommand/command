package org.mwolff.commons.command.samplecommands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mwolff.command.chain.XMLChainBuilder;
import org.mwolff.command.parameterobject.DefaultParameterObject;

public class ProcessTestCommandEndTest {

        @Test
    public void testEndCommand() throws Exception {
        final XMLChainBuilder<Object> xmlChainBuilder = new XMLChainBuilder<>(
                "/commandChainEnd.xml");
        final DefaultParameterObject context = new DefaultParameterObject();
        final String result = xmlChainBuilder.executeAsProcess("END", context);
        assertNull(result);
        String contextResult = context.getAsString("result");
        assertThat(contextResult, is("END - "));

    }
}
