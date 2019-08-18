package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DependiesSequenceReaderTest {

  @Test
  void testRead() throws IOException, URISyntaxException {

    // 1. Arrange
    URL url = getClass().getClassLoader().getResource("test-dependency-matrices.xlsx");
    Path path = Paths.get(url.toURI());
    DependiesSequenceReader dependiesSequenceReader = new DependiesSequenceReader(new FileInputStream(path.toFile()));

    // 2. Act
    DependiesSequence dependiesSequence = dependiesSequenceReader.read();

    // 3. Assert
    assertNotNull(dependiesSequence);
    assertNotNull(dependiesSequence.getDependiesList());
  }
}