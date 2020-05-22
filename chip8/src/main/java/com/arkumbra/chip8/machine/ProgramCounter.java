package com.arkumbra.chip8.machine;

public interface ProgramCounter {

  void increment();
  void skipNextInstruction();

  int getPosition();
  void goTo(int position);

  void push(int val);
  void pop();

  // for debugger
  void toggleFreezeExecution();
  void freeze();
  void unfreeze();
  void step();

}
