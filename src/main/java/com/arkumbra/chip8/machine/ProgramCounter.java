package com.arkumbra.chip8.machine;

public interface ProgramCounter {

  void increment();

  int getPosition();
  void goTo(char position);

  void push(char val);
  char pop();

}
