package com.arkumbra.chip8;

import com.arkumbra.chip8.opcode.OpCodeLabel;
import java.io.File;
import org.junit.Ignore;
import org.junit.Test;

public class Chip8Test {

//  @Ignore
  @Test
  public void test() {
//    String relativePath = "src/main/resources/Airplane.ch8";
//    String relativePath = "src/main/resources/15 Puzzle [Roger Ivie].ch8";
    String relativePath = "src/main/resources/BLINKY.ch8";
//    String relativePath = "src/main/resources/Deflection [John Fort].ch8";
//    String relativePath = "src/main/resources/Hi-Lo [Jef Winsor, 1978].ch8";
    String absolutePath = new File(relativePath).getAbsolutePath();

    Chip8 chip8 = new Chip8();
    chip8.start(absolutePath);

    try {
      OpCodeLabel lastCode;
      int i = 0;
      do {
       lastCode = chip8.runCycle();
//        System.out.println(i++ + " " + lastCode);
      } while (lastCode != OpCodeLabel.Op00EEReturn);

    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(chip8.dump());
  }

  @Test
  public void testHiLo() {
    String relativePath = "src/main/resources/Hi-Lo [Jef Winsor, 1978].ch8";
    String absolutePath = new File(relativePath).getAbsolutePath();

    Chip8 chip8 = new Chip8();
    chip8.start(absolutePath);

    // Force program counter to post-init step
    chip8.getMachine().getProgramCounter().goTo(536);

    try {
      OpCodeLabel lastCode;
      int i = 0;
      do {
        lastCode = chip8.runCycle();
        System.out.println(i++ + " " + lastCode);
      } while (lastCode != OpCodeLabel.Op00EEReturn);

    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(chip8.dump());
  }

}
