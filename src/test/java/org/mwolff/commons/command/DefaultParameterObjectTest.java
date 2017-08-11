package org.mwolff.commons.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultParameterObjectTest {
    
    @Test
    public void getStandardContextTest() throws Exception {
        
        GenericParameterObject context = DefaultParameterObject.getStandardContext();
        assertNotNull(context);
    }

    @Test
    public void getStandardContextWithValueTest() throws Exception {
        
        GenericParameterObject context = DefaultParameterObject.getStandardContext("file.list", "filelist");
        String value = context.getAsString("file.list");
        assertThat(value, is("filelist"));
    }
}
