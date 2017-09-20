/**
    Simple Command Framework.

    Framework for easy building software that fits the SOLID principles.
    @author Manfred Wolff <m.wolff@neusta.de>

    Download: https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git

    Copyright (C) 2018 Manfred Wolff and the simple command community

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
package org.mwolff.command.chain;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mwolff.command.Command;
import org.mwolff.command.CommandException;
import org.mwolff.command.CommandTransitionEnum.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.samplecommands.SimpleTestCommand;

import com.googlecode.catchexception.CatchException;

import static org.junit.Assert.assertThat;

@RunWith(JUnitPlatform.class)
public class AbstractDefaultChainCommandTest {

   @SuppressWarnings("deprecation")
   @Test
   void testExecute() throws Exception {
      final GenericParameterObject context = new DefaultParameterObject();
      final Command<GenericParameterObject> command = new SimpleTestCommand<>();
      command.execute(context);
      assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
      assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
   }

   @SuppressWarnings("deprecation")
   @Test
   void testExecuteNull() throws Exception {
      final Command<GenericParameterObject> command = new SimpleTestCommand<>();
      CatchException.catchException(command).execute(null);
      assertThat(CatchException.caughtException(), is(instanceOf(CommandException.class)));
   }

   @SuppressWarnings("deprecation")
   @Test
   void testExecuteAsChain() throws Exception {
      final GenericParameterObject context = new DefaultParameterObject();
      final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
      final boolean result = command.executeAsChain(context);
      assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
      assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
      assertThat(result, CoreMatchers.is(Boolean.TRUE));
   }

   @SuppressWarnings("deprecation")
   @Test
   void testExecuteAsChainNull() throws Exception {
      final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
      final boolean result = command.executeAsChain(null);
      assertThat(result, CoreMatchers.is(Boolean.FALSE));
   }

   @Test
   void testExecuteCommand() throws Exception {
      final GenericParameterObject context = new DefaultParameterObject();
      final Command<GenericParameterObject> command = new SimpleTestCommand<>();
      final CommandTransition result = command.executeCommand(context);
      assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
      assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
      assertThat(result, CoreMatchers.is(CommandTransition.SUCCESS));
   }

   @Test
   void testExecuteCommandNull() throws Exception {
      final Command<GenericParameterObject> command = new SimpleTestCommand<>();
      final CommandTransition result = command.executeCommand(null);
      assertThat(result, CoreMatchers.is(CommandTransition.FAILURE));
   }

   @Test
   void testExecuteCommandAsChain() throws Exception {
      final GenericParameterObject context = new DefaultParameterObject();
      final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
      final CommandTransition result = command.executeCommandAsChain(context);
      assertThat(context.getAsString("SimpleTestCommand"), CoreMatchers.is("SimpleTestCommand"));
      assertThat(context.getAsString("priority"), CoreMatchers.is("S-"));
      assertThat(result, CoreMatchers.is(CommandTransition.NEXT));
   }

   @Test
   void testExecuteCommandAsChainNull() throws Exception {
      final ChainCommand<GenericParameterObject> command = new SimpleTestCommand<>();
      final CommandTransition result = command.executeCommandAsChain(null);
      assertThat(result, CoreMatchers.is(CommandTransition.DONE));
   }

}
