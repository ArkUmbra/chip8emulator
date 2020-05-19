package com.arkumbra.startchip8.gdx;

import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.startchip8.gdx.chip8.GdxInputToKeyLabelMapper;
import com.badlogic.gdx.InputAdapter;

public class GdxInputProcessor extends InputAdapter {

  private KeyPressListener chip8KeyPressListener;

  public GdxInputProcessor(KeyPressListener chip8KeyPressListener) {
    this.chip8KeyPressListener = chip8KeyPressListener;
  }

  @Override
  public boolean keyDown(int keycode) {
    KeyLabel keyLabel = toChip8KeyLabel(keycode);
    if (keyLabel == null) {
      return false;
    }

    chip8KeyPressListener.keyPressed(keyLabel);
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    KeyLabel keyLabel = toChip8KeyLabel(keycode);
    if (keyLabel == null) {
      return false;
    }

    chip8KeyPressListener.keyReleased(keyLabel);
    return true;
  }

  private KeyLabel toChip8KeyLabel(int keyCode) {
    return GdxInputToKeyLabelMapper.fromKeyboardButton(keyCode);
  }

}
