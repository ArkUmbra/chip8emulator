package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Ram;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class GameLoaderTest {

  @Test
  public void test() throws IOException {
//    System.out.println(Integer.toHexString());

    String relativePath = "src/main/resources/15 Puzzle [Roger Ivie].ch8";
    String absolutePath = new File(relativePath).getAbsolutePath();

    Ram ram = new GameLoader().loadGameIntoMemory(absolutePath);
    System.out.println(ram.dump());
  }

}
