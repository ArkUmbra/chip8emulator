package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.ProgramCounter;

public class MemoryImpl implements Memory {

  private char[] memory;

  @Override
  public void load(char[] memory) {
    this.memory = memory;
  }

  @Override
  public char readRawOpCode(ProgramCounter programCounter) {
//    return 0;
    return memory[programCounter.getPosition()];
  }

  @Override
  public String dumpMemoryToHex() {
    StringBuilder sb = new StringBuilder();

    for (char c : memory) {
      // Can't find a method to do char -> hex directly, so split the char into two bytes
      sb.append(RadixUtils.asHexAndBinary(c));
//      sb.append(Integer.toUnsignedString((c & 0xFFFF), 16));
//      sb.append(Integer.toHexString((c & 0xFFFF)));
      sb.append(System.lineSeparator());
    }

    return sb.toString();
//    Arrays.

//    return Hex.enc(memory);
  }
}
