package org.mwolff.command.sax;

import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class SaxParameterObject implements GenericParameterObject {

    GenericParameterObject context = DefaultParameterObject.getInstance();

    @Override
    public Object get(String key) {
        return context.get(key);
    }

    public Object get(GlobalCommandConstants key) {
        return context.get(key.toString());
    }

    @Override
    public String getAsString(String key) {
        return context.getAsString(key);
    }

    public String getAsString(GlobalCommandConstants key) {
        return context.getAsString(key.toString());
    }

    @Override
    public void put(String key, Object value) {
        context.put(key, value);
    }

    public void put(GlobalCommandConstants key, Object value) {
        context.put(key.toString(), value);
    }

}
