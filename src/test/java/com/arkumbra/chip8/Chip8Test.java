package com.arkumbra.chip8;

import com.arkumbra.chip8.opcode.OpCodeLabel;
import java.io.File;
import org.junit.Test;

public class Chip8Test {

//  @Ignore
  @Test
  public void test() {
//    String relativePath = "src/main/resources/15 Puzzle [Roger Ivie].ch8";
    String relativePath = "src/main/resources/Airplane.ch8";
    String absolutePath = new File(relativePath).getAbsolutePath();

    Chip8 chip8 = new Chip8();
    chip8.start(absolutePath);

    try {
      for (int i = 0; i < 10; i++) {
        OpCodeLabel opCodeLabel = chip8.runCycle();
        System.out.println(i + " " + opCodeLabel);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(chip8.dump());
  }

}
