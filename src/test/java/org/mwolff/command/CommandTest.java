/** Simple Command Framework.
 *
 * Framework for easy building software that fits the SOLID principles.
 *
 * @author Manfred Wolff <m.wolff@neusta.de>
 *
 *         Download:
 *         https://github.com/simplecommand/command.git
 *
 *         Copyright (C) 2018-2021 Manfred Wolff and the simple command community
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
 *         02110-1301
 *         USA */

package org.mwolff.command;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mwolff.command.interfaces.ChainCommand;
import org.mwolff.command.interfaces.Command;
import org.mwolff.command.interfaces.CommandTransition;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.samplecommands.ExceptionCommand;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwolff.command.interfaces.CommandTransition.FAILURE;
import static org.mwolff.command.interfaces.CommandTransition.SUCCESS;

public class CommandTest {

   @Test
   public void testInterfaceDefaultExecuteCommand() {

      final GenericParameterObject context = new DefaultParameterObject();
      final Command<GenericParameterObject> command = new Command<GenericParameterObject>() {

         @Override
         public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
            return SUCCESS;
         }

      };

      final CommandTransition transition = command.executeCommand(context);
      assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
   }

   @Test
   public void testInterfaceDefaultExecuteWithNoContext() throws Exception {

      final Command<GenericParameterObject> command = new Command<GenericParameterObject>() {

         @Override
         public CommandTransition executeCommand(final GenericParameterObject parameterObject) {
            if (parameterObject == null) {
               return FAILURE;
            } else {
               return SUCCESS;
            }
         }

      };

      CommandTransition transition = command.executeCommand(null);
      assertThat(transition, CoreMatchers.is(FAILURE));
      transition = command.executeCommand(new DefaultParameterObject());
      assertThat(transition, CoreMatchers.is(SUCCESS));
   }

   @Test
   public void testDefaultBehaviorWithException() throws Exception {
      final ChainCommand<DefaultParameterObject> command = new ExceptionCommand<>();
      final DefaultParameterObject context = new DefaultParameterObject();
      command.executeCommandAsChain(context);
      final String result = context.getAsString("executed");
      assertEquals("true", result);
   }

}
