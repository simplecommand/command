package de.mwolff.command.chainbuilder;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.mwolff.commons.command.iface.BootstrapCommand;

public class PackageScannerTest {

    private static final Logger LOG = Logger.getLogger(PackageScanner.class);

    @SuppressWarnings("rawtypes")
    @Test
    public void testScanPackage() throws Exception {

        PackageScanner packageScanner = new PackageScanner();
        
        Set<Class<? extends BootstrapCommand>> scannedClasses = packageScanner.getSubTypesOf("de.mwolff.commons.command.bootstrapCommands");

        assertThat(scannedClasses.size(), is(2));
        
        for (Class<?> classEntry : scannedClasses) {
            LOG.info(classEntry.getName());
        }
        
    }
    
}
