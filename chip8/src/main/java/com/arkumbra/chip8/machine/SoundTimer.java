package com.arkumbra.chip8.machine;


import com.arkumbra.chip8.external.SoundService;

public class SoundTimer extends BaseTimer {

  private SoundService soundService;

  public SoundTimer(SoundService soundService) {
    this.soundService = soundService;
  }

  @Override
  public void tick() {
    if (getCurrentValue() > 0) {
      soundService.beep();
    }
  }

}
