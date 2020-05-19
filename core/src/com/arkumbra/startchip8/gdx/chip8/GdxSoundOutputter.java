package com.arkumbra.startchip8.gdx.chip8;

import com.arkumbra.chip8.machine.SoundOutputter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GdxSoundOutputter implements SoundOutputter {

  private Sound sound;


  public GdxSoundOutputter() {
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlip.wav"));
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlip2ShortSoft.wav"));
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlip3.wav"));
    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlipDropShort.wav"));
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlipOther.wav"));
  }

  @Override
  public void beep() {
    sound.stop();
    sound.play(0.4f);
  }
}
