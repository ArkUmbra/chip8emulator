package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Dumpable;
import com.arkumbra.chip8.machine.Font;
import com.arkumbra.chip8.machine.IndexRegister;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.SerializableData;

public interface Memory extends Dumpable, SerializableData {

  void load(Font font);

  void load(byte[] gameRom);

  char readRawOpCode(ProgramCounter programCounter);

  void write(int fromIndex, byte[] toWrite);

  byte[] readBytes(IndexRegister indexRegister, int bytesToRead);

}
