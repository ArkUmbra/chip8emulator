package com.arkumbra.chip8.machine;

public interface DataRegister {

  void add(char value);
  void set(char value);
  char get();

}
