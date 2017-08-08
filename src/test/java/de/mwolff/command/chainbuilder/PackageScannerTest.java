package de.mwolff.command.chainbuilder;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class PackageScannerTest {


    @Test
    public void testScanPackage() throws Exception {

        PackageScanner packageScanner = new PackageScanner();
        List<String> scannedClasses = packageScanner.getSubTypesOf("de.mwolff.commons.command.bootstrapCommands");
        assertThat(scannedClasses.size(), is(2));
    }
    
    @Test
    public void testScanPackageNotExists() throws Exception {
        PackageScanner packageScanner = new PackageScanner();
        List<String> scannedClasses = packageScanner.getSubTypesOf("de.mwolff.commons.command.bootstrapCommands.notexits");
        assertThat(scannedClasses.size(), is(0));
    }
    
}
