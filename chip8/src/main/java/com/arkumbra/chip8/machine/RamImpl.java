package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Logger;
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

public class RamImpl implements Ram {
  private final Logger logger = new Logger(getClass());
  private static final int CAPACITY = 0x1000;
  public static final int SERIALIZED_LENGTH = CAPACITY;

  // first 512 bytes are reserved. Add rom into memory in after that, followed by ram
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
  }

  @Override
  public void load(byte[] gameRom) {
    logger.debug("Game length in bytes " + gameRom.length);
    byte[] combinedMemory = new byte[CAPACITY];

    System.arraycopy(gameRom, 0,
        combinedMemory, RESERVED, gameRom.length);
    this.memory = combinedMemory;

    logger.debug("Game rom of " + gameRom.length + ", total memory size " + memory.length);
  }

  @Override
  public char readRawOpCode(ProgramCounter programCounter) {
    int pos = programCounter.getPosition();

    // combine two bytes to make an opcode
    byte byteLeft = memory[pos];
    byte byteRight = memory[pos + 1];

    // We need to add the bitmask on the right, to make Java not do weird stuff with widening
    return (char) ((byteLeft << 8) | byteRight & 0xFF);
  }

  @Override
  public void write(int indexFrom, byte[] toWrite) {
    System.arraycopy(toWrite, 0, memory, indexFrom, toWrite.length);
  }

  @Override
  public byte[] readBytes(IndexRegister indexRegister, int bytesToRead) {
    int start = indexRegister.get();
    return Arrays.copyOfRange(memory, start, start + bytesToRead);
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("---- Memory ----");
    sb.append(System.lineSeparator());

    for (int i = 0; i < memory.length - 1; i=i+2) {
      sb.append(Hex.encodeHex(new byte[]{memory[i], memory[i+1]}));
      sb.append(" ");

      if ((i + 2) % 16 == 0) {
        sb.append(System.lineSeparator());
      }
    }
    sb.append(System.lineSeparator());
    return sb.toString();
  }

  @Override
  public byte[] serialize() {
    return memory;
  }

  @Override
  public void deserialize(byte[] data) {
    this.memory = data;
  }
}
