package com.arkumbra.chip8;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class GameLoaderTest {

  @Test
  public void test() throws IOException {
    String relativePath = "src/main/resources/15 Puzzle [Roger Ivie].ch8";
    String absolutePath = new File(relativePath).getAbsolutePath();

    Memory memory = new GameLoader().loadGameIntoMemory(absolutePath);
    System.out.println(memory.dumpMemoryToHex());
  }

}
