package com.arkumbra.chip8.machine;

public interface Ram extends Dumpable, SerializableData {

  void load(Font font);
  void load(byte[] gameRom);

  char readRawOpCode(ProgramCounter programCounter);
  void write(int fromIndex, byte[] toWrite);
  byte[] readBytes(IndexRegister indexRegister, int bytesToRead);

}
