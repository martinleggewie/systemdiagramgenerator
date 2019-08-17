package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PumlGeneratorTest {

  @Test
  void testGenerate() throws IOException, URISyntaxException {
    // 1. Arrange
    String inputFilename = "systems-and-dependencies.xlsx";
    DependiesSequenceReader dependiesSequenceReader = new DependiesSequenceReader(inputFilename);
    DependiesSequence dependiesSequence = dependiesSequenceReader.read();

    // 2. Act
    dependiesSequence.getDependiesList().forEach(dependies -> {
      try {
        String pumlOutput = new PumlGenerator(dependies).generate();
        System.out.println(pumlOutput);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // 3. Assert ... currently missing
  }
}