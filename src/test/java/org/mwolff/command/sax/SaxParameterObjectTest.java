package org.mwolff.command.sax;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mwolff.command.sax.GlobalCommandConstants.ERROR_STRING;

public class SaxParameterObjectTest {

    @Test
    void attributeTransparency() {
        
        SaxParameterObject context = new SaxParameterObject();
        context.put(ERROR_STRING, "Error String.");
        String result = context.getAsString("ERROR_STRING");
        assertThat(result, is("Error String."));
    }
    
}
