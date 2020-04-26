package com.arkumbra.chip8.machine;

public interface KeyPressListener {

  void keyPressed(KeyLabel keyLabel);
  void keyReleased(KeyLabel keyLabel);

}
