package org.mwolff.command.sax;

import org.junit.Test;

public class GlobalCommandConstantsTest {

    @Test
    public void testName() throws Exception {
        GlobalCommandConstants.valueOf(GlobalCommandConstants.FILE_NAME.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.INPUT_SOURCE.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.ERROR_STRING.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.ACTION_LIST.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.COMMAND_CONTAINER.toString());
    }

}
