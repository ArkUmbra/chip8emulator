package com.arkumbra.chip8.machine;

public interface IndexRegister {

  /**
   *
   * @param value up to 0xFFFF
   */
  void set(int value);

  /**
   *
   * @param value
   * @return true if add causes index register to overflow (I+value>0xFFF)
   */
  boolean add(int value);

  int get();

}
