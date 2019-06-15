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
package org.mwolff.command;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mwolff.command.chain.ChainCommand;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;
import org.mwolff.command.samplecommands.ExceptionCommand;

import static org.mwolff.command.CommandTransition.FAILURE;
import static org.mwolff.command.CommandTransition.SUCCESS;

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
      Assert.assertThat(transition, CoreMatchers.is(CommandTransition.SUCCESS));
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
      Assert.assertThat(transition, CoreMatchers.is(FAILURE));
      transition = command.executeCommand(DefaultParameterObject.NULLCONTEXT);
      Assert.assertThat(transition, CoreMatchers.is(SUCCESS));
   }

   @Test
   public void testDefaultBehaviorWithException() throws Exception {
      final ChainCommand<DefaultParameterObject> command = new ExceptionCommand<>();
      final DefaultParameterObject context = new DefaultParameterObject();
      command.executeCommandAsChain(context);
      final String result = context.getAsString("executed");
      Assert.assertEquals("true", result);
   }

}
