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

package org.mwolff.command.process;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.builder.XMLChainBuilder;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultEndCommandTest {

   private DefaultEndCommand defaultEndCommand;

   @BeforeEach
   public void setUp() {
      defaultEndCommand = new DefaultEndCommand();
   }

   @Test
   public void testExecuteAsProcessSimple() throws Exception {
      assertThat(defaultEndCommand.executeAsProcess(null), is("END"));
   }

   @Test
   public void testEndCommandInProcessChain() throws Exception {
      final DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
      defaultEndCommand.setProcessID("END");
      final DefaultParameterObject context = new DefaultParameterObject();
      defaultCommandContainer.addCommand(defaultEndCommand);
      assertThat(defaultCommandContainer.executeAsProcess("END", context), nullValue());
   }

   @Test
   public void testExecuteAsProcessComplex() throws Exception {
      assertThat(defaultEndCommand.executeAsProcess("START", null), is("END"));
   }

   @Test
   public void testCommandWithoutTransiton() throws Exception {
      final XMLChainBuilder<GenericParameterObject> xmlChainBuilder = new XMLChainBuilder<>(
            "commandChainProcessEnd.xml");
      String result = xmlChainBuilder.executeAsProcess("END", new DefaultParameterObject());
      assertThat(result, nullValue());

   }

   @Test
   public void testCommandWithTransitonFails() throws Exception {
      final XMLChainBuilder<GenericParameterObject> xmlChainBuilder = new XMLChainBuilder<>(
            "commandChainProcessEndFails.xml");
      String result = xmlChainBuilder.executeAsProcess("End", new DefaultParameterObject());
      assertThat(result, nullValue());
   }

}
