package com.arkumbra.chip8.machine;


import com.arkumbra.chip8.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;

public class ScreenImpl implements Screen, ScreenMemory, Dumpable {
  private final Logger logger = new Logger(getClass());
  private static final int WIDTH = 64;
  private static final int HEIGHT = 32;

  private static final List<int[]> breakpointPixels = List.of(
      new int[]{0, 1}
      ,new int[]{0, 2}
//      ,new int[]{60, 1}
  );

  private boolean[][] pixels = new boolean[WIDTH][HEIGHT];

  @Override
  public void clearScreen() {
    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        pixels[x][y] = false;
      }
    }
  }

  @Override
  public boolean draw(byte bitFlagsToDraw, int fromX, int y) {
    boolean atLeastOnePixelFlipped = false;

//    System.out.println(BinaryCodec.toAsciiString(new byte[]{bitFlagsToDraw}));

    for (int[] breakpoint : breakpointPixels) {
      if (breakpoint[0] == fromX && breakpoint[1] == y) {
        int bx = breakpoint[0];
        int by = breakpoint[1];
        System.out.println("Breakpoint trigger [" + bx + ", " + by + "], currently set to " + pixels[bx][by]);
      }
    }

    for (int i = 0; i < 8; i++) {
      boolean bit = getBitAsBoolean(bitFlagsToDraw, (7 - i));
      boolean flipped = writeBitToScreen(bit, fromX + i, y);
      logger.debug("Was flipped " + flipped);
      atLeastOnePixelFlipped = atLeastOnePixelFlipped || flipped;
//      atLeastOnePixelFlipped = atLeastOnePixelFlipped || !bit;
    }

    return atLeastOnePixelFlipped;
  }

  /**
   *
   * @param bit set or not set
   * @param x requested x position. Will wrap if off screen
   * @param y requested y position
   * @return true if screen bit was unset due to this
   */
  private boolean writeBitToScreen(boolean bit, int x, int y) {
    //if (x > WIDTH) return false; // TODO this seems to fix certain things, but not sure...


    x %= WIDTH; // wrap if over width
//    y %= HEIGHT;


    // unset when pixel was previously on, and this write would turn it off
    boolean unset = pixels[x][y] && !bit;
    pixels[x][y] = bit;

//    return bit;
    return unset;
  }

  public boolean getBitAsBoolean(byte flags, int pos) {
//    flags = (byte)(flags & 0b11111111);
//    logger.debug(Integer.toHexString(flags) + ":" + Integer.toBinaryString(flags));
    int bitFlag = (flags >> pos) & 1;
    return bitFlag > 0;
  }

  @Override
  public boolean[][] getPixels() {
    return pixels;
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- Screen ---").append(System.lineSeparator());
    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        boolean pixel = pixels[x][y];
        String bit = pixel ? "#" : ".";
        sb.append(bit);
      }
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }
}
