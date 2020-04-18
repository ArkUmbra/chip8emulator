package com.arkumbra.chip8;

public class MemoryImpl implements Memory {

  private short[] memory;

  @Override
  public void load(short[] memory) {
    this.memory = memory;
  }

  // Java can't do unsigned so using ints and shorts, instead of shorts and bytes in places...
  @Override
  public int readRawOpCode(ProgramCounter programCounter) {
    // opcode = memory[pc] << 8 | memory[pc + 1];
    short leftByte = memory[programCounter.getPosition()];
    short rightByte = memory[programCounter.getPosition() + 1];

    // shift left byte over one bytes worth, OR the right byte in next to it
    return leftByte << 8 | rightByte;
  }
}
