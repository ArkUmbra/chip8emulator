package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Dumpable;
import com.arkumbra.chip8.machine.ProgramCounter;

public interface Memory extends Dumpable {

  void load(byte[] memory);

  char readRawOpCode(ProgramCounter programCounter);

  byte[] readBytes(ProgramCounter programCounter, int bytesToRead);

}
