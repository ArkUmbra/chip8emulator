package com.arkumbra.chip8.machine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class SoundTimerTest {

  @Test
  public void test() {
    SoundOutputter soundOutputter = mock(SoundOutputter.class);

    SoundTimer soundTimer = new SoundTimer(soundOutputter);
    soundTimer.set((char)2);
    soundTimer.tick();

    verify(soundOutputter, times(1))
        .beep();
  }

}
