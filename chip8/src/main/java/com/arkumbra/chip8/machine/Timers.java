package com.arkumbra.chip8.machine;

public class Timers {

  private final Timer delayTimer;
  private final Timer soundTimer;

  public Timers(Timer delayTimer, Timer soundTimer) {
    this.delayTimer = delayTimer;
    this.soundTimer = soundTimer;
  }

  public Timer getDelayTimer() {
    return delayTimer;
  }

  public Timer getSoundTimer() {
    return soundTimer;
  }
}
