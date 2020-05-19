package com.arkumbra.chip8.bitmask;

public class EmptyBitmask extends BitMask {

  private static final char bitmask = 0x0000;

  public EmptyBitmask() {
    super(bitmask);
  }

}
