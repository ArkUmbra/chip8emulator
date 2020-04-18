package com.arkumbra.chip8;

import com.arkumbra.chip8.opcode.OpCode;

public interface Memory {

  void load(short[] memory);

  int readRawOpCode(ProgramCounter programCounter);
}
