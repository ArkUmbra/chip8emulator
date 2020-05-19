package com.arkumbra.chip8.machine;

public interface ScreenMemory {
  public static final int WIDTH = 64;
  public static final int HEIGHT = 32;

  boolean[][] getPixels();

}
