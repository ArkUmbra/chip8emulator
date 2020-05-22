package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Logger;
import java.nio.ByteBuffer;

public class ScreenImpl implements Screen, ScreenMemory, SerializableData, Dumpable {
  public static final int SERIALIZED_LENGTH = WIDTH * HEIGHT;

  private final Logger logger = new Logger(getClass());

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
      logger.debug("Was flipped " + flipped);
      atLeastOnePixelFlipped = atLeastOnePixelFlipped || flipped;
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
    x %= WIDTH; // wrap if over width
    y %= HEIGHT;

    boolean startState = pixels[x][y];

    // XOR
    boolean set = pixels[x][y] != bit;

    pixels[x][y] = set;

    return startState && !set;
  }

  public boolean getBitAsBoolean(byte flags, int pos) {
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

  @Override
  public byte[] serialize() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(SERIALIZED_LENGTH);
    byte on = 1;
    byte off = 0;

    for (int y = 0; y < pixels[0].length; y++) {
      for (int x = 0; x < pixels.length; x++) {
        boolean pixel = pixels[x][y];
        byteBuffer.put(pixel ? on : off);
      }
    }

    return byteBuffer.array();
  }

  @Override
  public void deserialize(byte[] data) {
    if (data.length != SERIALIZED_LENGTH) {
      throw new RuntimeException("Not valid length " + data.length);
    }

    ByteBuffer byteBuffer = ByteBuffer.wrap(data);

    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        byte pixel = byteBuffer.get();
        pixels[x][y] = pixel == 1;
      }
    }
  }
}
