package com.arkumbra.chip8.machine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.external.SoundService;
import org.junit.Test;

public class SoundTimerTest {

  @Test
  public void test() {
    SoundService soundService = mock(SoundService.class);

    SoundTimer soundTimer = new SoundTimer(soundService);
    soundTimer.set((char)2);
    soundTimer.tick();

    verify(soundService, times(1))
        .beep();
  }

}
