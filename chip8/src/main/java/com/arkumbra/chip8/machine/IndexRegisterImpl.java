package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Logger;

public class IndexRegisterImpl implements IndexRegister {
  private final Logger logger = new Logger(getClass());

  // up to 4095
  private int indexValue;

  @Override
  public void set(int value) {
    this.indexValue = (value % 4096);

    logger.debug("Input value " + value + ", actually set to " + indexValue);
  }

  @Override
  public boolean add(int value) {
    this.indexValue = ((this.indexValue + value) % 4096);
    logger.debug("Add value " + value + " actually set to " + indexValue);

    return indexValue != value;
  }

  @Override
  public int get() {
    return indexValue;
  }
}
