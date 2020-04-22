package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.ProgramCounter;

public interface Memory {

  void load(char[] memory);

  char readRawOpCode(ProgramCounter programCounter);

  byte[] readBytes(ProgramCounter programCounter, int bytesToRead);

  String dumpMemoryToHex();
}
