package de.mwolff.commons.command.samplecommands;

import de.mwolff.commons.command.AbstractDefaultProcessCommand;
import de.mwolff.commons.command.GenericContext;
import de.mwolff.commons.command.iface.CommandException;

public class ProcessTestCommandNext<T extends GenericContext> extends AbstractDefaultProcessCommand<T> {

    public ProcessTestCommandNext() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param processID
     */
    public ProcessTestCommandNext(String processID) {
        super(processID);
    }

    @Override
    public void execute(T context) throws CommandException {
    }

    @Override
    public String executeAsProcess(String startCommand, T context) {

        // Decision: The first time we redirect to Start, otherwise we end it.
        String result = context.getAsString("result");
        result += processID + " - ";
        context.put("result", result);

        Integer counter = (Integer) context.get("counter");
        if (counter == null) {
            counter = Integer.valueOf(1);
            context.put("counter", counter);
            return "Start";

        }
        return "";
    }

    @Override
    public String getProcessID() {
        return super.getProcessID();
    }

}
