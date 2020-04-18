package com.arkumbra.chip8;

public interface ProgramCounter {

  void increment();

  int getPosition();
  void goTo(char position);

  void push(char val);
  char pop();

}
