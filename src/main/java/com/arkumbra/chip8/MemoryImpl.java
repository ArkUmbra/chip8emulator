package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.ProgramCounter;
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

public class MemoryImpl implements Memory {

  // first 512 bytes are reserved. Add game memory in after that
  public static final int RESERVED = 512;

  private byte[] memory;

  @Override
  public void load(byte[] gameFile) {
    // font data etc..
    byte[] combinedMemory = new byte[RESERVED + gameFile.length];

    System.arraycopy(gameFile, 0,
        combinedMemory, RESERVED, gameFile.length);
    this.memory = combinedMemory;
  }

  @Override
  public char readRawOpCode(ProgramCounter programCounter) {
    int pos = programCounter.getPosition();

    byte byteLeft = memory[pos];
    byte byteRight = memory[pos + 1];

    // We need to add the bitmask on the right, to make Java not do weird stuff with widening
    char opCode = (char) ((byteLeft << 8) | byteRight & 0xFF);

    System.out.println("Reading op code " + Hex.encodeHexString(new byte[]{byteLeft, byteRight}));
    return opCode;
  }

  @Override
  public byte[] readBytes(ProgramCounter programCounter, int bytesToRead) {
    return Arrays.copyOfRange(memory, programCounter.getPosition(), bytesToRead);
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("---- Machine ----");
    sb.append(System.lineSeparator());

    for (int i = 0; i < memory.length - 1; i++) {
      sb.append(Hex.encodeHex(new byte[]{memory[i], memory[i+1]}));
      sb.append(" ");

      if (i % 10 == 0) {
        sb.append(System.lineSeparator());
      }
    }

    return sb.toString();
  }
}
