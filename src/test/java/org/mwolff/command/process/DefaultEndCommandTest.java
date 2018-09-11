/** Simple Command Framework.
 * 
 * Framework for easy building software that fits the SOLID principles.
 * 
 * @author Manfred Wolff <m.wolff@neusta.de>
 * 
 *         Download:
 *         https://mwolff.info:7990/bitbucket/scm/scf/simplecommandframework.git
 * 
 *         Copyright (C) 2018 Manfred Wolff and the simple command community
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

import static org.hamcrest.CoreMatchers.nullValue;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwolff.command.DefaultCommandContainer;
import org.mwolff.command.chain.XMLChainBuilder;
import org.mwolff.command.parameterobject.DefaultParameterObject;
import org.mwolff.command.parameterobject.GenericParameterObject;

public class DefaultEndCommandTest {

   private DefaultEndCommand defaultEndCommand;

   @BeforeEach
   public void setUp() {
      defaultEndCommand = new DefaultEndCommand();
   }

   @Test
   public void testExecuteAsProcessSimple() throws Exception {
      Assert.assertThat(defaultEndCommand.executeAsProcess(null), CoreMatchers.is("END"));
   }

   @Test
   public void testEndCommandInProcessChain() throws Exception {
      final DefaultCommandContainer<GenericParameterObject> defaultCommandContainer = new DefaultCommandContainer<>();
      defaultEndCommand.setProcessID("END");
      final DefaultParameterObject context = new DefaultParameterObject();
      defaultCommandContainer.addCommand(defaultEndCommand);
      Assert.assertThat(defaultCommandContainer.executeAsProcess("END", context), CoreMatchers.is("END"));
   }

   @Test
   public void testExecuteAsProcessComplex() throws Exception {
      Assert.assertThat(defaultEndCommand.executeAsProcess("START", null), CoreMatchers.is("END"));
   }

   @Test
   public void testCommandWithoutTransiton() throws Exception {
      final XMLChainBuilder<GenericParameterObject> xmlChainBuilder = new XMLChainBuilder<>(
            "commandChainProcessEnd.xml");
      String result = xmlChainBuilder.executeAsProcess("End", DefaultParameterObject.NULLCONTEXT);
      Assert.assertThat(result, CoreMatchers.is("END"));

   }

   @Test
   public void testCommandWithTransitonFails() throws Exception {
      final XMLChainBuilder<GenericParameterObject> xmlChainBuilder = new XMLChainBuilder<>(
            "commandChainProcessEndFails.xml");
      String result = xmlChainBuilder.executeAsProcess("End", DefaultParameterObject.NULLCONTEXT);
      Assert.assertThat(result, nullValue());
   }

}
