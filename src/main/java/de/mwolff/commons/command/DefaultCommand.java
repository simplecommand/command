/**
 * Simple command framework.
 *
 * Framework for easy building software that fits the open-close-principle.
 * @author Manfred Wolff <wolff@manfred-wolff.de>
 *         (c) neusta software development
 */
package de.mwolff.commons.command;

import org.apache.log4j.Logger;

/**
 * Default implementation for a command. You may use <code>executeAsChain</code>
 * for all executions of the <code>command</code> or
 * <code>commandContainer</code>.
 */
public class DefaultCommand<T extends Context> implements Command<T> {

    private static final Logger LOG = Logger.getLogger(DefaultCommand.class);
    protected String processID;

    /** Constructor with process ID */
    public DefaultCommand(String processID) {
        this.processID = processID;
    }

    /** Default constructor */
    public DefaultCommand() {
        super();
    }

    @Override
    /**
     * @see de.mwolff.commons.command.Command#getProcessID()
     */
    public String getProcessID() {
        return this.processID;
    }

    /**
     * @see de.mwolff.commons.command.Command#execute(de.mwolff.commons.command.Context)
     */
    @Override
    public void execute(T context) throws CommandException {
        // has to be implemented
    }

    /**
     * @see de.mwolff.commons.command.Command#executeAsChain(de.mwolff.commons.command.Context)
     */
    @Override
    public boolean executeAsChain(T context) {
        boolean result = true;
        try {
            execute(context);
        } catch (final Exception e) {
            LOG.info("Chain is aborted.", e);
            result = false;
        }
        return result;
    }

    /**
     * @see de.mwolff.commons.command.Command#executeAsProcess(de.mwolff.commons.command.Context)
     */
    @Override
    public String executeAsProcess(String startCommand, T context) {
        // has to be implemented
        return "End";
    }

}
