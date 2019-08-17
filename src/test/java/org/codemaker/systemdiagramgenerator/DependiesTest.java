package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DependiesTest {

  @Test
  void testGetDependies() {
    // 1. Arrange
    Dependies deppendies = new Dependies("(unknown)");
    Dependy dependy1 = createDependy("sys1", "sys2)");
    Dependy dependy2 = createDependy("sys3", "sys4)");
    Dependy dependy3 = createDependy("sys5", "sys6)");
    deppendies.addDependy(dependy1);
    deppendies.addDependy(dependy2);
    deppendies.addDependy(dependy3);

    // 2. Act
    Set<Dependy> dependies = deppendies.getDependies();

    // 3. Assert
    assertNotNull(dependies);
    assertTrue(dependies.contains(dependy1));
    assertTrue(dependies.contains(dependy2));
    assertTrue(dependies.contains(dependy3));
  }

  @Test
  void testGetSyses() {
    // 1. Arrange
    Sys sys1 = new Sys("sys1", "thing");
    Sys sys2 = new Sys("sys2", "thing");
    Sys sys3 = new Sys("sys3", "thing");
    Sys sys4 = new Sys("sys4", "thing");
    Sys sys5 = new Sys("sys5", "thing");
    Dependies dependies = new Dependies("(unknown)");
    Dependy dependy1 = new Dependy(sys1, sys1, Dependy.Type.STAYING);
    Dependy dependy2 = new Dependy(sys1, sys2, Dependy.Type.STAYING);
    Dependy dependy3 = new Dependy(sys1, sys2, Dependy.Type.STAYING);
    Dependy dependy4 = new Dependy(sys1, sys3, Dependy.Type.STAYING);
    Dependy dependy5 = new Dependy(sys1, sys4, Dependy.Type.STAYING);
    Dependy dependy6 = new Dependy(sys2, sys3, Dependy.Type.STAYING);
    Dependy dependy7 = new Dependy(sys2, sys4, Dependy.Type.STAYING);
    Dependy dependy8 = new Dependy(sys3, sys4, Dependy.Type.STAYING);
    Dependy dependy9 = new Dependy(sys4, sys5, Dependy.Type.STAYING);
    dependies.addDependy(dependy1);
    dependies.addDependy(dependy2);
    dependies.addDependy(dependy3);
    dependies.addDependy(dependy4);
    dependies.addDependy(dependy5);
    dependies.addDependy(dependy6);
    dependies.addDependy(dependy7);
    dependies.addDependy(dependy8);
    dependies.addDependy(dependy9);

    // 2. Act
    Set<Sys> syses = dependies.getSyses();

    // 3. Assert
    assertEquals(5, syses.size());
    assertTrue((syses.contains(sys1)));
    assertTrue((syses.contains(sys2)));
    assertTrue((syses.contains(sys3)));
    assertTrue((syses.contains(sys4)));
    assertTrue((syses.contains(sys5)));
  }

  private Dependy createDependy(String sys1Name, String sys2Name) {
    return new Dependy(new Sys(sys1Name, "thing"), new Sys(sys2Name, "thing"), Dependy.Type.STAYING);
  }
}