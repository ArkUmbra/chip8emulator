package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Font;
import com.arkumbra.chip8.machine.FontLabel;
import com.arkumbra.chip8.machine.ProgramCounter;
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

public class MemoryImpl implements Memory {

  // first 512 bytes are reserved. Add game memory in after that
  public static final int RESERVED = 512;

  private byte[] memory;

  @Override
  public void load(Font font) {
    int currentIndex = 0;
    for (FontLabel fontLabel : FontLabel.values()) {
      byte[] fontCharacter= font.getFontDataAndSetFixedAddressLocation(currentIndex, fontLabel);
      System.arraycopy(fontCharacter, 0, memory, currentIndex, fontCharacter.length);
      currentIndex += fontCharacter.length;
    }

    // DEBUG
    for (FontLabel fontLabel : FontLabel.values()) {
      int address = font.getAddress(fontLabel);
      System.out.println("Font character " + fontLabel.name() + " stored at " + address);
    }
  }

  @Override
  public void load(byte[] gameRom) {
    // font data etc..
    byte[] combinedMemory = new byte[RESERVED + gameRom.length];

    System.arraycopy(gameRom, 0,
        combinedMemory, RESERVED, gameRom.length);
    this.memory = combinedMemory;
  }

  @Override
  public char readRawOpCode(ProgramCounter programCounter) {
    int pos = programCounter.getPosition();

    byte byteLeft = memory[pos];
    byte byteRight = memory[pos + 1];

    // We need to add the bitmask on the right, to make Java not do weird stuff with widening
    char opCode = (char) ((byteLeft << 8) | byteRight & 0xFF);

//    System.out.println("Reading op code " + Hex.encodeHexString(new byte[]{byteLeft, byteRight}));
    return opCode;
  }

  @Override
  public void write(int indexFrom, byte[] toWrite) {
    for (int offset = 0; offset < toWrite.length; offset++) {
      memory[indexFrom + offset] = toWrite[offset];
    }
  }

  @Override
  public byte[] readBytes(ProgramCounter programCounter, int bytesToRead) {
    int start = programCounter.getPosition();
    return Arrays.copyOfRange(memory, start, start + bytesToRead);
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("---- Memory ----");
    sb.append(System.lineSeparator());

    for (int i = 0; i < memory.length - 1; i++) {
      sb.append(Hex.encodeHex(new byte[]{memory[i], memory[i+1]}));
      sb.append(" ");

      if ((i + 1) % 8 == 0) {
        sb.append(System.lineSeparator());
      }
    }

    return sb.toString();
  }
}
