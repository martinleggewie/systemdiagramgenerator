package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DependyMatrixTest {

    @Test
    void testGetDependies() {
        // 1. Arrange
        DependyMatrix dependyMatrix = new DependyMatrix();
        Dependy dependy1 = new Dependy(new Sys("sys1"), new Sys("sys2"));
        Dependy dependy2 = new Dependy(new Sys("sys3"), new Sys("sys4"));
        Dependy dependy3 = new Dependy(new Sys("sys5"), new Sys("sys6"));
        dependyMatrix.addDependy(dependy1);
        dependyMatrix.addDependy(dependy2);
        dependyMatrix.addDependy(dependy3);

        // 2. Act
        Set<Dependy> dependies = dependyMatrix.getDependies();

        // 3. Assert
        assertNotNull(dependies);
        assertTrue(dependies.contains(dependy1));
        assertTrue(dependies.contains(dependy2));
        assertTrue(dependies.contains(dependy3));
    }

    @Test
    void testGetSyses() {
        // 1. Arrange
        Sys sys1 = new Sys("sys1");
        Sys sys2 = new Sys("sys2");
        Sys sys3 = new Sys("sys3");
        Sys sys4 = new Sys("sys4");
        Sys sys5 = new Sys("sys5");
        DependyMatrix dependyMatrix = new DependyMatrix();
        Dependy dependy1 = new Dependy(sys1, sys1);
        Dependy dependy2 = new Dependy(sys1, sys2);
        Dependy dependy3 = new Dependy(sys1, sys2);
        Dependy dependy4 = new Dependy(sys1, sys3);
        Dependy dependy5 = new Dependy(sys1, sys4);
        Dependy dependy6 = new Dependy(sys2, sys3);
        Dependy dependy7 = new Dependy(sys2, sys4);
        Dependy dependy8 = new Dependy(sys3, sys4);
        Dependy dependy9 = new Dependy(sys4, sys5);
        dependyMatrix.addDependy(dependy1);
        dependyMatrix.addDependy(dependy2);
        dependyMatrix.addDependy(dependy3);
        dependyMatrix.addDependy(dependy4);
        dependyMatrix.addDependy(dependy5);
        dependyMatrix.addDependy(dependy6);
        dependyMatrix.addDependy(dependy7);
        dependyMatrix.addDependy(dependy8);
        dependyMatrix.addDependy(dependy9);

        // 2. Act
        Set<Sys> syses = dependyMatrix.getSyses();

        // 3. Assert
        assertEquals(5, syses.size());
        assertTrue((syses.contains(sys1)));
        assertTrue((syses.contains(sys2)));
        assertTrue((syses.contains(sys3)));
        assertTrue((syses.contains(sys4)));
        assertTrue((syses.contains(sys5)));
    }
}