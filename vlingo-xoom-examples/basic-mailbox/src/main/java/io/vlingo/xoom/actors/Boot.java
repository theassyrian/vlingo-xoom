// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.xoom.actors;

import io.vlingo.actors.World;

public class Boot {
  private static World xoomBootWorld;

  public static void main(final String[] args) {
    final String name = args.length > 0 ? args[0] : "vlingo-xoom";

    xoomBootWorld = start(name);
  }

  public static World xoomBootWorld() {
    return xoomBootWorld;
  }

  /**
   * Answers a new {@code World} with the given {@code name} and that is configured with
   * the contents of the {@code vlingo-xoom.properties} file.
   * @param name the {@code String} name to assign to the new {@code World} instance
   * @return {@code World}
   */
  public static World start(final String name) {
    xoomBootWorld = World.start(name, io.vlingo.xoom.actors.Properties.properties);

    return xoomBootWorld;
  }
}
