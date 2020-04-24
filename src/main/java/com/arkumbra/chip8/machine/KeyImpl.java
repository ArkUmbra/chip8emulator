package com.arkumbra.chip8.machine;

public class KeyImpl implements Key, PressableKey {

  private boolean isPressed = false;

  @Override
  public boolean isPressed() {
    return isPressed;
  }

  @Override
  public void press(boolean onOrOff) {
    this.isPressed = onOrOff;
  }

}
