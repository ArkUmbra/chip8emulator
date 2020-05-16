package com.arkumbra.chip8.external;

import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import java.io.IOException;

public class ConsoleScreenOutputter implements ScreenOutputter {
  private static final String PIXEL_ON_CHARACTER = "#";
  private static final String PIXEL_OFF_CHARACTER = ".";

  private static final ProcessBuilder clearProcess = new ProcessBuilder("cmd", "/c", "cls").inheritIO();

  private ScreenMemory screenMemory;

  @Override
  public void init(ScreenMemory screenMemory, KeyPressListener keyPressListener) {
    this.screenMemory = screenMemory;

  }

  @Override
  public void drawFrame() {
    boolean[][] pixelsToSet = screenMemory.getPixels();
    StringBuffer sb = new StringBuffer();

    for (int y = 0; y < pixelsToSet[0].length; y++) {
      for (int x = 0; x < pixelsToSet.length; x++) {
        sb.append(
            pixelsToSet[x][y] ? PIXEL_ON_CHARACTER : PIXEL_OFF_CHARACTER
        );
      }
      sb.append(System.lineSeparator());
    }

    //clearScreen();
    System.out.println(sb.toString());
  }

  private void clearScreen() {
    try {
      clearProcess.start().waitFor();
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }


}
