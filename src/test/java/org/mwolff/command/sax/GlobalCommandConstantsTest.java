/*         Simple Command Framework.
 *
 *         Framework for easy building software that fits the SOLID principles.
 *
 *         @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Copyright (C) 2017-2020 Manfred Wolff and the simple command community
 *
 *         This library is free software; you can redistribute it and/or
 *         modify it under the terms of the GNU Lesser General Public
 *         License as published by the Free Software Foundation; either
 *         version 2.1 of the License, or (at your option) any later version.
 *
 *         This library is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         Lesser General Public License for more details.
 *
 *         You should have received a copy of the GNU Lesser General Public
 *         License along with this library; if not, write to the Free Software
 *         Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *         02110-1301 USA
 */
package org.mwolff.command.sax;

import org.junit.jupiter.api.Test;

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
