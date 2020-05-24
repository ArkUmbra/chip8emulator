package com.arkumbra.chip8.state;

public interface SaveStateHandler {

  byte[] createSaveState();
  void loadFromSaveStateAndRun(byte[] state);
  void stepStateBackAndPause();
}
