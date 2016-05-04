/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>
    Download: https://github.com/simplecommand/SimpleCommandFramework


    Copyright (C) 2015 neusta software development

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
    USA
 */
package de.mwolff.testtool;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 */
public class TestAppender extends AppenderSkeleton {

    final List<LoggingEvent> log = new ArrayList<LoggingEvent>();

    /*
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.
     * LoggingEvent)
     */
    @Override
    protected void append(final LoggingEvent loggingEvent) {
        log.add(loggingEvent);
    }

    /*
     * @see org.apache.log4j.Appender#close()
     */
    @Override
    public void close() {
        // YTODO Auto-generated method stub

    }

    public final List<LoggingEvent> getLog() {
        return new ArrayList<LoggingEvent>(log);
    }

    /*
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    @Override
    public boolean requiresLayout() {
        return false;
    }

}
