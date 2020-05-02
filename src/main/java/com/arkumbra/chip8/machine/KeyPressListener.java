package com.arkumbra.chip8.machine;

public interface KeyPressListener {

  boolean isPressed(KeyLabel keyLabel);
  void keyPressed(KeyLabel keyLabel);
  void keyReleased(KeyLabel keyLabel);

}
