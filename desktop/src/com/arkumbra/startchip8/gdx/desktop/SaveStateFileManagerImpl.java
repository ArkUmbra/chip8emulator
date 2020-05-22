package com.arkumbra.startchip8.gdx.desktop;

import com.arkumbra.startchip8.gdx.SaveStateFileManager;

public class SaveStateFileManagerImpl implements SaveStateFileManager {

  // temporary
  private byte[] state;

  @Override
  public void saveFile(byte[] state) {
    this.state = state;
  }

  @Override
  public byte[] load() {
    return state;
  }

}
