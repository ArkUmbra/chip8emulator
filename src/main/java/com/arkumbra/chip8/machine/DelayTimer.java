package com.arkumbra.chip8.machine;


public class DelayTimer implements Timer, TickUpdateable {

  private char value = 0;

  @Override
  public char getCurrentValue() {
    return value;
  }

  @Override
  public void set(char value) {
    this.value = value;
  }

  @Override
  public void tick() {
    if (value > 0) {
      value--;
    }
  }

}
