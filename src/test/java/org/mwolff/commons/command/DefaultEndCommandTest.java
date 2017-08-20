package org.mwolff.commons.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DefaultEndCommandTest {

    private DefaultEndCommand<GenericParameterObject> defaultEndCommand;

    @Before
    public void setUp() {
        defaultEndCommand = new DefaultEndCommand<>();
    }
    
    @Test
    public void testExecuteAsProcessSimple() throws Exception {
        assertThat(defaultEndCommand.executeAsProcess(DefaultParameterObject.NULLCONTEXT), is("END"));
    }

    @Test
    public void testExecuteAsProcessComplex() throws Exception {
        assertThat(defaultEndCommand.executeAsProcess("START", DefaultParameterObject.NULLCONTEXT), is("END"));
    }
    
    @Test
    public void testExecuteOnly() throws Exception {
        assertThat(defaultEndCommand.executeAsChain(DefaultParameterObject.NULLCONTEXT), is(Boolean.FALSE));
    }
}
