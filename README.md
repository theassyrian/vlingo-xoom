# vlingo-xoom

[![Javadocs](http://javadoc.io/badge/io.vlingo/vlingo-xoom.svg?color=brightgreen)](http://javadoc.io/doc/io.vlingo/vlingo-xoom) [![Build Status](https://travis-ci.org/vlingo/vlingo-xoom.svg?branch=master)](https://travis-ci.org/vlingo/vlingo-xoom) [ ![Download](https://api.bintray.com/packages/vlingo/vlingo-platform-java/vlingo-xoom/images/download.svg) ](https://bintray.com/vlingo/vlingo-platform-java/vlingo-xoom/_latestVersion) [![Gitter chat](https://badges.gitter.im/gitterHQ/gitter.png)](https://gitter.im/vlingo-platform-java)

Vlingo Xoom provides a JVM microframework based on Micronaut and vlingo/PLATFORM for building high-performance reactive microservices.

![](https://imgur.com/YFVeIz1.png)

Xoom is currently in incubation mode. This repository is considered experimental and constantly evolving. To build a Xoom microservice, check out the available examples.

- [Basic Microservice Example](https://github.com/vlingo/vlingo-xoom/tree/master/vlingo-xoom-examples/basic-microservice)
- [Advanced Microservice Example](https://github.com/vlingo/vlingo-xoom/tree/master/vlingo-xoom-examples/advanced-microservice)
- Domain-driven Microservices (Coming soon)

### Bintray

```xml
  <repositories>
    <repository>
      <id>jcenter</id>
      <url>https://jcenter.bintray.com/</url>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>io.vlingo</groupId>
      <artifactId>vlingo-xoom</artifactId>
      <version>0.9.1-RC2</version>
      <scope>compile</scope>
    </dependency>
  </dependencies>
```

```gradle
dependencies {
    compile 'io.vlingo:vlingo-xoom:0.9.1-RC2'
}

repositories {
    jcenter()
}
```

License (See LICENSE file for full license)
-------------------------------------------
This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.
