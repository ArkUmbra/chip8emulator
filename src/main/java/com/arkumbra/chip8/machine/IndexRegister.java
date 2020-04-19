package com.arkumbra.chip8.machine;

public interface IndexRegister {

  /**
   *
   * @param value up to 0xFFFF
   */
  void set(int value);

  int get();

}
