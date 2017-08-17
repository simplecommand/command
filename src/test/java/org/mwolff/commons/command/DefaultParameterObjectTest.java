package org.mwolff.commons.command;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class DefaultParameterObjectTest {

    @Test
    public void getStandardContextTest() throws Exception {

        final GenericParameterObject context = DefaultParameterObject.getStandardContext();
        Assert.assertNotNull(context);
    }

    @Test
    public void getStandardContextWithValueTest() throws Exception {

        final GenericParameterObject context = DefaultParameterObject.getStandardContext("file.list", "filelist");
        final String value = context.getAsString("file.list");
        Assert.assertThat(value, CoreMatchers.is("filelist"));
    }
}
