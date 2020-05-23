package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Ram;
import com.arkumbra.chip8.machine.RamImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameLoader {

  public Ram loadGameIntoMemory(String absoluteFilePath) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(absoluteFilePath));

    Ram ram = new RamImpl();
    ram.load(bytes);
    return ram;
  }

}
