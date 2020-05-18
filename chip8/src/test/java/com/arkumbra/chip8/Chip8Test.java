package com.arkumbra.chip8;

import com.arkumbra.chip8.external.JPanelOutputter;
import com.arkumbra.chip8.external.ScreenOutputter;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import java.io.File;
import org.junit.Test;

public class Chip8Test {

//  @Ignore
  @Test
  public void test() {
//    String relativePath = "src/main/resources/INVADERS.ch8";
//    String relativePath = "src/main/resources/Airplane.ch8";
    String relativePath = "src/main/resources/15 Puzzle [Roger Ivie].ch8";
//    String relativePath = "src/main/resources/BLINKY.ch8";
//    String relativePath = "src/main/resources/Deflection [John Fort].ch8";
//    String relativePath = "src/main/resources/Hi-Lo [Jef Winsor, 1978].ch8";
//    String relativePath = "src/main/resources/TANK.ch8";
//    String relativePath = "src/main/resources/CONNECT4.ch8";
    String absolutePath = new File(relativePath).getAbsolutePath();

    ScreenOutputter outputter = new JPanelOutputter();
//    RaylibOuputter outputter = new RaylibOuputter();
    Chip8 chip8 = new Chip8(outputter);
    chip8.loadGame(absolutePath);

    try {
      OpCodeLabel lastCode;
      int i = 0;
      do {
       lastCode = chip8.runSingleCycle();
//        System.out.println(i++ + " " + lastCode);
      } while (lastCode != OpCodeLabel.Op00EEReturn);
//      } while (lastCode != OpCodeLabel.OpDXYNDraw);
//
    } catch (Exception e) {
      e.printStackTrace();
    }

//    System.out.println(chip8.dump());
  }


}
