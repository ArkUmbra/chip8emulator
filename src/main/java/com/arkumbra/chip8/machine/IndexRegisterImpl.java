package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Logger;

public class IndexRegisterImpl implements IndexRegister {

  // up to 4095
  private int indexValue;

  @Override
  public void set(int value) {
//    this.indexValue = value;
//    this.indexValue = (value & 0xFFF);

    int capped = (value % 4096);
    this.indexValue = capped;

    Logger.debug("Input value " + value + ", actually set to " + capped);
  }

  @Override
  public boolean add(int value) {
//    int capped = (value > 4095) ? 4095
    int capped = ((this.indexValue + value) % 4096);

    Logger.debug("Add value " + value + " current " + indexValue + ", actually set to " + capped);
    this.indexValue = capped;


    return value != capped;
  }

  @Override
  public int get() {
    return indexValue;
  }
}
