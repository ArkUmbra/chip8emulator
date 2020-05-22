package com.arkumbra.startchip8.gdx;

public interface SaveStateFileManager {

  void saveFile(byte[] state);

  byte[] load();

}