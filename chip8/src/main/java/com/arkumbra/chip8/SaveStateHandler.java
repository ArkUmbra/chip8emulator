package com.arkumbra.chip8;

public interface SaveStateHandler {

  byte[] createSaveState();
  void loadFromSaveState(byte[] state);
}
