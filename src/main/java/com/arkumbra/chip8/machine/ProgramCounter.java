package com.arkumbra.chip8.machine;

public interface ProgramCounter {

  void increment();
  void skipNextInstruction();

  int getPosition();
  void goTo(char position);

  void push(char val);
  void pop();

}
