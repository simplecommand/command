package de.mwolff.command.chainbuilder;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.mwolff.commons.command.DefaultParameterObject;
import de.mwolff.commons.command.GenericParameterObject;
import de.mwolff.commons.command.iface.ChainBuilder;
import de.mwolff.commons.command.iface.ParameterObject;

public class BootstrapChainBuilderTest {
    
    
    private static final Logger LOG = Logger.getLogger(BootstrapChainBuilderTest.class);
    
    @Test
    public void chainbuilderExists() throws Exception {
        final BootstrapChainBuilder<ParameterObject> bootstrapChainBuilder = new BootstrapChainBuilder<ParameterObject>(
                "");
        Assert.assertThat(bootstrapChainBuilder, CoreMatchers.instanceOf(ChainBuilder.class));
    }

    @Test
    public void testBuildChain() throws Exception {
        final BootstrapChainBuilder<ParameterObject> bootstrapChainBuilder = new BootstrapChainBuilder<ParameterObject>(
                "de.mwolff.commons.command.bootstrapCommands");
        final GenericParameterObject context = new DefaultParameterObject();
        bootstrapChainBuilder.execute(context);
        final String priorString = context.getAsString("priority");
        System.out.println("priorString: " + priorString);
        
        assertThat(priorString, Matchers.is("One-Two-"));
    }

}
