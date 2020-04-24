package com.arkumbra.chip8.machine;


public class ScreenImpl implements Screen, ScreenMemory, Dumpable {
  private static final int WIDTH = 64;
  private static final int HEIGHT = 32;

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

    for (int i = 0; i < 8; i++) {
      boolean bit = getBitAsBoolean(bitFlagsToDraw, (7 - i));
      boolean flipped = writeBitToScreen(bit, fromX + i, y);
      atLeastOnePixelFlipped = !atLeastOnePixelFlipped && flipped;
    }

    return atLeastOnePixelFlipped;

    // TODO rather than asking the screen outputter to draw here, should probably do in reverse
    //  i.e. get screen outputter to ask screen for memory and output on timer
  }

  /**
   *
   * @param bit set or not set
   * @param x requested x position. Will wrap if off screen
   * @param y requested y position
   * @return true if screen bit was unset due to this
   */
  private boolean writeBitToScreen(boolean bit, int x, int y) {
    x = x % WIDTH; // wrap if over width

    // unset when pixel was previously on, and this write would turn it off
    boolean unset = pixels[x][y] && !bit;
    pixels[x][y] = bit;

    return unset;
  }

  private boolean getBitAsBoolean(byte flags, int pos) {
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
