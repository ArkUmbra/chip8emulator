package com.arkumbra.chip8.machine;

public interface Keys {

  public Key getKey(KeyLabel keyLabel);

  /**
   * Blocks until next key is pressed
   * @return label of whichever key was pressed
   */
  KeyLabel waitForNextKeyPress();

}
