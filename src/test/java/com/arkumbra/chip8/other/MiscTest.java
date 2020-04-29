package com.arkumbra.chip8.other;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class MiscTest {

  @Test
  public void debug() throws IOException {

    Path filePath = Path.of("/Users/lukegardener/Code/GithubProjects/chip8emulator/blinkyDebugOutput.log");
//    Path filePath = Path.of("/Users/lukegardener/Code/GithubProjects/chip8emulator/otherEmulatorBlinkyOutput.log");
    List<String> allLines = Files.readAllLines(filePath);

    Set<String> uniqueInstructions = new LinkedHashSet<>(allLines);

    System.out.println("Found unique instructions " + uniqueInstructions.size());
    uniqueInstructions.forEach(System.out::println);
  }

}
