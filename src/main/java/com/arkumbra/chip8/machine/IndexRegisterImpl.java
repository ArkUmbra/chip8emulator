package com.arkumbra.chip8.machine;

public class IndexRegisterImpl implements IndexRegister {

  // up to 4095
  private int indexValue;

  @Override
  public void set(int value) {
    this.indexValue = (value & 0xFFF);
  }

  @Override
  public boolean add(int value) {
    int capped = (this.indexValue + value & 0xFFF);
    this.indexValue = capped;

    return value > capped;
  }

  @Override
  public int get() {
    return indexValue;
  }
}
