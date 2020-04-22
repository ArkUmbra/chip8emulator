package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Memory;

public interface Machine extends TickUpdateable {

  RoutineRunner getRoutineRunner();
  Screen getScreen();
  ProgramCounter getProgramCounter();
  Registers getRegisters();
  IndexRegister getIndexRegister();
  Memory getMemory();
  Keys getKeys();
  Timers getTimers();

}
