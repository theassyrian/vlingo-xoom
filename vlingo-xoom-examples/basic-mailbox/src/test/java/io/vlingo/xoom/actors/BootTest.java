// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.actors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.vlingo.actors.World;

public class BootTest {
  private static final String BootWorldName = "test-boot";

  @Test
  public void testThatWorldBoots() {
    Boot.main(new String[] { BootWorldName });

    final World world = Boot.xoomBootWorld();

    assertEquals(BootWorldName, world.name());
  }

  @Test
  public void testThatWorldStarts() {
    final World world = Boot.start(BootWorldName);

    assertEquals(BootWorldName, world.name());
  }
}
