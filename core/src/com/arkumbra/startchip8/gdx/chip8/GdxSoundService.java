package com.arkumbra.startchip8.gdx.chip8;

import com.arkumbra.chip8.external.SoundService;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class GdxSoundService implements SoundService {

  private Sound sound;


  public void init() {
    //    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlip.wav"));
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlip2ShortSoft.wav"));
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlip3.wav"));
//    this.sound = Gdx.audio.newSound(Gdx.files.internal("soundBlipOther.wav"));


    Files files = Gdx.files;
    FileHandle soundFile = files.internal("soundBlipDropShort.wav");
    this.sound = Gdx.audio.newSound(soundFile);
  }

  @Override
  public void beep() {
    if (sound == null) {
      return;
    }

    sound.stop();
    sound.play(0.4f);
  }
}
