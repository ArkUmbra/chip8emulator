package com.arkumbra.chip8.external;


import com.arkumbra.chip8.machine.ScreenMemory;

public interface ScreenOutputter {

  void init(ScreenMemory screenMemory);
  void drawFrame();

}
