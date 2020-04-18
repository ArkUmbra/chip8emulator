package com.arkumbra.chip8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameLoader {

  public Memory loadGameIntoMemory(String filePath) throws IOException {
    byte[] bytes = Files.readAllBytes(Path.of(filePath));

    char[] opcodes = new char[bytes.length / 2];
    int cCounter = 0;

    for (int i = 0; i < bytes.length; i += 2) {
      byte byteLeft = bytes[i];
      byte byteRight = bytes[i + 1];
      char opCode = (char) (byteLeft << 8 | byteRight);

      opcodes[cCounter] = opCode;
      cCounter++;
    }

    Memory memory = new MemoryImpl();
    memory.load(opcodes);
    return memory;
  }

}
