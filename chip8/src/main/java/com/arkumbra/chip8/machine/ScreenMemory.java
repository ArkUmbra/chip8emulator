package com.arkumbra.chip8.machine;

public interface ScreenMemory {
  int WIDTH = 64;
  int HEIGHT = 32;

  boolean[][] getPixels();

}
