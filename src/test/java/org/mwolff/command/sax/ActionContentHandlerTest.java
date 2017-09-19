package org.mwolff.command.sax;

import org.junit.Test;

public class ActionContentHandlerTest {

    // Actually this is JAX-B. The whole handler is tested transitiv except som
    // methods, which have to implement but are empty.
    // This is a mess but a typically problem dealing with frameworks.
    @Test
    public void testEmptyMethods() throws Exception {
        ActionContentHandler actionContentHandler = new ActionContentHandler();
        actionContentHandler.skippedEntity("");
        actionContentHandler.ignorableWhitespace(null, 2, 4);
        actionContentHandler.processingInstruction("", "");
    }
    
}
