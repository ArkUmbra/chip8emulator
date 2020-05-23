package com.arkumbra.chip8.machine;

public interface Machine extends TickUpdateable {

  Screen getScreen();
  ProgramCounter getProgramCounter();
  DataRegisters getRegisters();
  IndexRegister getIndexRegister();
  Ram getRam();
  Keys getKeys();
  Timer getDelayTimer();
  Timer getSoundTimer();
  Font getFont();

  void loadIntoMemory(Ram ram);

}
