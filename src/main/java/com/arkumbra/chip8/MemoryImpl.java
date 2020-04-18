package com.arkumbra.chip8;

import org.apache.commons.codec.binary.Hex;

public class MemoryImpl implements Memory {

  private char[] memory;

  @Override
  public void load(char[] memory) {
    this.memory = memory;
  }

  @Override
  public char readRawOpCode(ProgramCounter programCounter) {
    return memory[programCounter.getPosition()];
  }

  @Override
  public String dumpMemoryToHex() {
    StringBuilder sb = new StringBuilder();

    for (char c : memory) {
      // Can't find a method to do char -> hex directly, so split the char into two bytes
      String hex = Hex.encodeHexString(new byte[]{
          (byte)(c >> 8),
          (byte)(c)
      });
      sb.append(hex);
      sb.append(System.lineSeparator());
    }

    return sb.toString();
  }
}
