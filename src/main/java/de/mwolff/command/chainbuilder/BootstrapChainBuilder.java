package de.mwolff.command.chainbuilder;

import java.util.List;

import org.apache.log4j.Logger;

import de.mwolff.commons.command.DefaultCommandContainer;
import de.mwolff.commons.command.iface.BootstrapCommand;
import de.mwolff.commons.command.iface.ChainBuilder;
import de.mwolff.commons.command.iface.Command;
import de.mwolff.commons.command.iface.CommandContainer;
import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;

/**
 * Generic chain builder for configuration with a package. All Commands within
 * this package gets part of the chain.
 */
public class BootstrapChainBuilder<T extends ParameterObject> implements ChainBuilder<T> {
    
    private static final Logger LOG = Logger.getLogger(BootstrapChainBuilder.class);

    private String myPackage;

    public BootstrapChainBuilder(final String mypackage) {
        this.myPackage = mypackage;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public CommandContainer<T> buildChain() {
        final CommandContainer<T> commandContainer = new DefaultCommandContainer<T>();

        PackageScanner scanner = new PackageScanner();
        List<String> scannedClasses  = scanner.getSubTypesOf(myPackage);
        LOG.info("Packagepath is: " + myPackage);

        for (String classToInstantiate : scannedClasses) {

            BootstrapCommand<T> command = null;
            try {
                LOG.info("ClassEntry is: " + classToInstantiate);
                command = (BootstrapCommand<T>) Class.forName(classToInstantiate).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                LOG.error(e);
            }
            commandContainer.addCommand(command.getPriority(),command);
        }

        return commandContainer;
    }

    @Override
    public void execute(T context) throws CommandException {
        buildChain().execute(context);
    }

    @Override
    public String executeAsProcess(String startCommand, T context) {
        return buildChain().executeAsProcess(startCommand, context);
    }

    @Override
    public String getProcessID() {
        return null;
    }

    @Override
    public void setProcessID(String processID) {
        throw new IllegalArgumentException("ProcessID cannot be set on Container.");
    }

    @Override
    public boolean executeAsChain(T context) {
        return buildChain().executeAsChain(context);
    }
}
