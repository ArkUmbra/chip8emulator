package com.arkumbra.chip8.machine;


public class SoundTimer extends BaseTimer {

  private SoundOutputter soundOutputter;

  public SoundTimer(SoundOutputter soundOutputter) {
    this.soundOutputter = soundOutputter;
  }

  @Override
  public void tick() {
    if (getCurrentValue() > 0) {
      soundOutputter.beep();
    }
  }

}
