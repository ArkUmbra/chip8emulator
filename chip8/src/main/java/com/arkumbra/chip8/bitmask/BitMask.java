package com.arkumbra.chip8.bitmask;

public class BitMask {
  private final char bitmask;

  BitMask(char bitmask) {
    this.bitmask = bitmask;
  }

  public final char applyMask(char data) {
    return (char) (data & bitmask);
  }

}
