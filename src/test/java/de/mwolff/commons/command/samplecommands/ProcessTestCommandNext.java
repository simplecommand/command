package de.mwolff.commons.command.samplecommands;

import org.apache.log4j.Logger;

import de.mwolff.commons.command.AbstractDefaultProcessCommand;
import de.mwolff.commons.command.GenericParameterObject;
import de.mwolff.commons.command.iface.CommandException;

public class ProcessTestCommandNext<T extends GenericParameterObject> extends AbstractDefaultProcessCommand<T> {

    private static final Logger LOG = Logger.getLogger(ProcessTestCommandNext.class);

    public ProcessTestCommandNext() {
        super();
    }

    public ProcessTestCommandNext(final String processID) {
        super(processID);
    }

    @Override
    public void execute(final T context) throws CommandException {
        String result = context.getAsString("result");
        result += processID + " - ";
        context.put("result", result);
    }

    @Override
    public String executeAsProcess(final String startCommand, final T context) {

        try {
            execute(context);
        } catch (final CommandException e) {
            LOG.error(e);
        }

        // Decision: The first time we redirect to Start, otherwise we end it.
        Integer counter = (Integer) context.get("counter");
        if (counter == null) {
            counter = Integer.valueOf(1);
            context.put("counter", counter);
            return "OK";
        }
        return "";
    }

    @Override
    public String getProcessID() {
        return super.getProcessID();
    }

}
