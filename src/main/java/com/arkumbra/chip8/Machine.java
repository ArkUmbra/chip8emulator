package com.arkumbra.chip8;

import com.arkumbra.chip8.screen.Screen;

public interface Machine {

  RoutineRunner getRoutineRunner();
  Screen getScreen();
  ProgramCounter getProgramCounter();
  Registers getRegisters();

}
