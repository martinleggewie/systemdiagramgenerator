package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DependiesSequenceReaderTest {

  @Test
  void testRead() throws IOException, URISyntaxException {
    // 1. Arrange
    DependiesSequenceReader dependiesSequenceReader = new DependiesSequenceReader("systems-and-dependencies.xlsx");

    // 2. Act
    DependiesSequence dependiesSequence = dependiesSequenceReader.read();

    // 3. Assert
    assertNotNull(dependiesSequence);
    assertNotNull(dependiesSequence.getDependiesList());
  }
}