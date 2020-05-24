package com.arkumbra.chip8.machine;

import java.nio.ByteBuffer;

// Needs work probably
// TODO fix to account for debug breakpoints
public class BaseTimer implements Timer {

  public static final int SERIALIZED_LENGTH = Character.BYTES + Long.BYTES;

  private final int COUNT_RATE_HERTZ = 60;
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

  @Override
  public byte[] serialize() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(SERIALIZED_LENGTH);
    byteBuffer.putChar(inputTimerValue);
    byteBuffer.putLong(timeAtSet);
    return byteBuffer.array();
  }

  @Override
  public void deserialize(byte[] data) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(data);
    inputTimerValue = byteBuffer.getChar();
    timeAtSet = byteBuffer.getLong();
  }
}
