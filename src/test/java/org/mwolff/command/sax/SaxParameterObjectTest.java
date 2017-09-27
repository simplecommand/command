package org.mwolff.command.sax;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mwolff.command.sax.GlobalCommandConstants.*;

import org.junit.jupiter.api.Test;

public class SaxParameterObjectTest {

    @Test
    void attributeTransparency() {
        
        SaxParameterObject context = new SaxParameterObject();
        context.put(ERROR_STRING, "Error String.");
        String result = context.getAsString("ERROR_STRING");
        assertThat(result, is("Error String."));
    }
    
}
