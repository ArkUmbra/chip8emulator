package com.arkumbra.chip8;

public interface Memory {

  void load(char[] memory);

  char readRawOpCode(ProgramCounter programCounter);

  String dumpMemoryToHex();
}
