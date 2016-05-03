package de.mwolff.commons.command;

import org.apache.log4j.Logger;

import de.mwolff.commons.command.iface.CommandException;
import de.mwolff.commons.command.iface.ParameterObject;

public class DefaultEndCommand<T extends ParameterObject> extends AbstractDefaultProcessCommand<T> {

    private static final Logger LOG = Logger.getLogger(DefaultEndCommand.class);

    @Override
    public void execute(final T context) throws CommandException {
        LOG.error("nothing to do");
    }

    @Override
    public String executeAsProcess(final String startCommand, final T context) {
        return "END";
    }

}
