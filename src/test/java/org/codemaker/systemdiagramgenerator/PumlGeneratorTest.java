package org.codemaker.systemdiagramgenerator;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


class PumlGeneratorTest {

  @Test
  void testGenerate() throws IOException, URISyntaxException {
    // 1. Arrange
    URL url = getClass().getClassLoader().getResource("test-dependency-matrices.xlsx");
    Path path = Paths.get(url.toURI());
    DependiesSequenceReader dependiesSequenceReader = new DependiesSequenceReader(new FileInputStream(path.toFile()));
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