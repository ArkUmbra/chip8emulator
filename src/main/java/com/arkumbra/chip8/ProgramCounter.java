package com.arkumbra.chip8;

public interface ProgramCounter {

  void increment();

  int getPosition();
  void goTo(short position);

  void push(short val);
  short pop();

}
