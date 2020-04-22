package com.arkumbra.chip8.machine;

import java.awt.Toolkit;

public class SoundTimer implements Timer, TickUpdateable {

  private char value = 0;

  private SoundOutputter soundOutputter;

  public SoundTimer(SoundOutputter soundOutputter) {
    this.soundOutputter = soundOutputter;
  }

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
      soundOutputter.beep();
      value--;
    }
  }

}
