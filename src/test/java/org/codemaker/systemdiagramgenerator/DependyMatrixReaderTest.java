package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DependyMatrixReaderTest {

    @Test
    void testRead() throws IOException, URISyntaxException {
        // 1. Arrange
        DependyMatrixReader dependyMatrixReader = new DependyMatrixReader("systems-and-dependencies.xlsx");

        // 2. Act
        DependyMatrix dependyMatrix = dependyMatrixReader.read();

        // 3. Assert
        assertNotNull(dependyMatrix);
        assertNotNull(dependyMatrix.getSyses());
        assertEquals(30, dependyMatrix.getSyses().size());
        assertNotNull(dependyMatrix.getDependies());
        assertEquals(43, dependyMatrix.getDependies().size());

    }
}