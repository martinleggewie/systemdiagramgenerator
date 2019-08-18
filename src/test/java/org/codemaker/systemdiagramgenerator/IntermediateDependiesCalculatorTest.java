package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntermediateDependiesCalculatorTest {

  @Test
  void testNotChangedAtAll() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(3, dependySet.size());
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3, MigrationState.STAYING)));
  }

  @Test
  void testSysAdded() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(3, dependySet.size());
    Sys sys3Added = new Sys(sys3.getName(), sys3.getType(), MigrationState.ABOUT_TO_BE_ADDED);
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3Added, MigrationState.ABOUT_TO_BE_ADDED)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3Added, MigrationState.ABOUT_TO_BE_ADDED)));
  }

  @Test
  void testSysRemoved() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(3, dependySet.size());
    Sys sys3Removed = new Sys(sys3.getName(), sys3.getType(), MigrationState.ABOUT_TO_BE_REMOVED);
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3Removed, MigrationState.ABOUT_TO_BE_REMOVED)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3Removed, MigrationState.ABOUT_TO_BE_REMOVED)));
  }

  @Test
  void testSysAddedAndRemoved() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);
    Sys sys4 = new Sys("sys4", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys1, sys4, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys2, sys4, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(5, dependySet.size());
    Sys sys3Removed = new Sys(sys3.getName(), sys3.getType(), MigrationState.ABOUT_TO_BE_REMOVED);
    Sys sys4Added = new Sys(sys4.getName(), sys4.getType(), MigrationState.ABOUT_TO_BE_ADDED);
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3Removed, MigrationState.ABOUT_TO_BE_REMOVED)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3Removed, MigrationState.ABOUT_TO_BE_REMOVED)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys4Added, MigrationState.ABOUT_TO_BE_ADDED)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys4Added, MigrationState.ABOUT_TO_BE_ADDED)));
  }

  @Test
  void testDependyAdded() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(3, dependySet.size());
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3, MigrationState.ABOUT_TO_BE_ADDED)));
  }

  @Test
  void testDependyRemoved() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(3, dependySet.size());
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3, MigrationState.ABOUT_TO_BE_REMOVED)));
  }

  @Test
  void testDependyAddedAndRemoved() {

    // 1. Arrange
    Sys sys1 = new Sys("sys1", "app", MigrationState.STAYING);
    Sys sys2 = new Sys("sys2", "app", MigrationState.STAYING);
    Sys sys3 = new Sys("sys3", "app", MigrationState.STAYING);

    Dependies dependies1 = new Dependies("dependies1");
    dependies1.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies1.addDependy(new Dependy(sys2, sys3, MigrationState.STAYING));

    Dependies dependies2 = new Dependies("dependies2");
    dependies2.addDependy(new Dependy(sys1, sys2, MigrationState.STAYING));
    dependies2.addDependy(new Dependy(sys1, sys3, MigrationState.STAYING));

    // 2. Act
    Dependies dependies1Towards2 = new IntermediateDependiesCalculator(dependies1, dependies2).calculate();

    // 3. Assert
    assertEquals("dependies1 --> dependies2", dependies1Towards2.getTitle());
    Set<Dependy> dependySet = dependies1Towards2.getDependySet();
    assertNotNull(dependySet);
    assertEquals(3, dependySet.size());
    assertTrue(dependySet.contains(new Dependy(sys1, sys2, MigrationState.STAYING)));
    assertTrue(dependySet.contains(new Dependy(sys1, sys3, MigrationState.ABOUT_TO_BE_ADDED)));
    assertTrue(dependySet.contains(new Dependy(sys2, sys3, MigrationState.ABOUT_TO_BE_REMOVED)));
  }

}