package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Dumpable;
import com.arkumbra.chip8.machine.Font;
import com.arkumbra.chip8.machine.ProgramCounter;

public interface Memory extends Dumpable {

  void load(Font font);

  void load(byte[] gameRom);

  char readRawOpCode(ProgramCounter programCounter);

  void write(ProgramCounter programCounter, byte[] toWrite);

  byte[] readBytes(ProgramCounter programCounter, int bytesToRead);

}
