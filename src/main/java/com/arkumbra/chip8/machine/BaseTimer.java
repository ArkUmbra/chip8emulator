package com.arkumbra.chip8.machine;

public class BaseTimer implements Timer {
  private final int COUNT_RATE_HERTZ = 30;
  private final int MS_PER_COUNT = 1000 / COUNT_RATE_HERTZ;

  private char inputTimerValue;
  private long timeAtSet;

  /**
   * Value decreases at a fixed frequency
   * @return
   */
  @Override
  public char getCurrentValue() {
    if (timeAtSet == 0) {
      return 0;
    }

    long timeDifferenceMs = System.currentTimeMillis() - timeAtSet;

    long ticksSince = timeDifferenceMs / MS_PER_COUNT;
    long result = inputTimerValue - ticksSince;

    return (result < 0) ? 0 : (char)result;
  }

  @Override
  public void set(char value) {
    this.inputTimerValue = value;
    this.timeAtSet = System.currentTimeMillis();
  }

  @Override
  public void tick() {
    // override as required
  }
}
