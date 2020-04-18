package com.arkumbra.chip8;

public interface ProgramCounter {

  int getPosition();
  void push(short val);
  short pop();

}
