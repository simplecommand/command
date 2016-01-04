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
package de.mwolff.commons.command.iface;

/**
 * Exception which works with this framework.
 *
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {
    
    public CommandException() {
        super();
    }
     
    public CommandException(final String message) {
        super(message);
    }
    
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CommandException(Throwable cause) {
        super(cause);
    }
}
