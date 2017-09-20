package org.mwolff.command.sax;

import org.junit.Test;
import org.mwolff.command.CommandTransition;

public class GlobalCommandConstantsTest {

    @Test
    public void testName() throws Exception {
        GlobalCommandConstants.valueOf(GlobalCommandConstants.file_name.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.input_source.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.error_string.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.action_list.toString());
        GlobalCommandConstants.valueOf(GlobalCommandConstants.command_container.toString());
    }
    
}
