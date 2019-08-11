package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class PumlGeneratorTest {

    @Test
    void testGenerate() throws IOException, URISyntaxException {
        // 1. Arrange
        String inputFilename = "systems-and-dependencies.xlsx";
        String outputFilename = "/stuff/ein-system.puml";
        DependyMatrixReader dependyMatrixReader = new DependyMatrixReader(inputFilename);
        DependyMatrix dependyMatrix = dependyMatrixReader.read();

        // 2. Act
        PumlGenerator pumlGenerator = new PumlGenerator(dependyMatrix, outputFilename);
        String pumlOutput = pumlGenerator.generate();

        // 3. Assert
        String expectedStart = "@startuml ein-system.puml";
        assertEquals(expectedStart, pumlOutput.substring(0, expectedStart.length()));

        System.out.println(pumlOutput);
    }
}