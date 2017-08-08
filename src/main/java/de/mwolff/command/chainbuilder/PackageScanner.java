package de.mwolff.command.chainbuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import de.mwolff.commons.command.iface.BootstrapCommand;

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

    /**
     * Gets all classes in a package.
     * 
     * @param packagePath
     *            Full qualified package path e.g. de.neusta.bootstrap.test
     * @return All classes in the selected path
     */
    @SuppressWarnings("rawtypes")
    public Set<Class<? extends BootstrapCommand>> getSubTypesOf(String packagePath) {

        LOG.trace("Grabbing packagePath: " + packagePath);
        
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        System.out.println(packagePath);
        
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(
                        false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packagePath))));

        Set<Class<? extends BootstrapCommand>> classes = reflections.getSubTypesOf(BootstrapCommand.class);
        
        LOG.trace("Returns size: " + classes.size());
        
        return classes;
    }
}
