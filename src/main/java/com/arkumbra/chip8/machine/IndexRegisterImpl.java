package com.arkumbra.chip8.machine;

public class IndexRegisterImpl implements IndexRegister {

  // up to 4095
  private int indexValue;

  @Override
  public void set(int value) {
//    this.indexValue = value;
//    this.indexValue = (value & 0xFFF);

    int capped = (value % 4096);
    this.indexValue = capped;

    System.out.println("Input value " + value + ", actually set to " + capped);
  }

  @Override
  public boolean add(int value) {
//    int capped = (value > 4095) ? 4095
    int capped = ((this.indexValue + value) % 4096);

    System.out.println("Add value " + value + " current " + indexValue + ", actually set to " + capped);
    this.indexValue = capped;


    return value != capped;
  }

  @Override
  public int get() {
    return indexValue;
  }
}
