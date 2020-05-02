package com.arkumbra.chip8.machine;

public class DataRegisterImpl implements DataRegister {

  private char registerContents = 0;

  @Override
  public void add(char value) {
    this.registerContents += value;
    capValue();
  }

  @Override
  public void set(char value) {
    this.registerContents = value;
    capValue();
  }

  @Override
  public char get() {
    return registerContents;
  }

  private void capValue() {
    // Make sure the contents stay capped within the expected 8 bits of the original Chip8 register
    // Also, Java is signed so we have to use a char instead of just a byte
//    while (registerContents > 255) {
//      this.registerContents = (char) -255;
//    }

    this.registerContents %= 255;
  }
}
