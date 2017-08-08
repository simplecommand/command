package de.mwolff.command.chainbuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Scans a certain package and returns all classes that belongs in this package
 * (test classes as well).
 * 
 * Uses org.reflections library to scan.
 * 
 * @author mwolff
 *
 */
public class PackageScanner {

    private static final Logger LOG = Logger.getLogger(PackageScanner.class);

    public List<String> getSubTypesOf(String packagePath) {
        
        
        List<String> resultList = new ArrayList<>();
        LOG.debug("Package path: " + packagePath ) ;
        
        String workPath = packagePath;
        workPath = workPath.replace(".", "/");
        workPath = "src/main/java/" + workPath;
                
        File f = new File(workPath);
        File[] fileArray = f.listFiles();
        
        if(fileArray == null) {
            return resultList;
        }
        
        for (File file : fileArray) {
            String filename = file.getName();
            filename = filename.replace(".java", "");
            filename = packagePath + "." + filename;
            LOG.debug("Scanned file: " + filename);
            resultList.add(filename);
        }
        
        return resultList;
    }
}
