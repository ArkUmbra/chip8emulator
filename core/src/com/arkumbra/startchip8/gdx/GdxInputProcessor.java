package com.arkumbra.startchip8.gdx;

import com.arkumbra.chip8.state.SaveStateHandler;
import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.startchip8.gdx.chip8.GdxInputToKeyLabelMapper;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class GdxInputProcessor extends InputAdapter {

  private final KeyPressListener chip8KeyPressListener;
  private final SaveStateHandler saveStateHandler;
  private final SaveStateFileManager saveStateFileManager;

  public GdxInputProcessor(KeyPressListener chip8KeyPressListener, SaveStateHandler saveStateHandler, SaveStateFileManager saveStateFileManager) {
    this.chip8KeyPressListener = chip8KeyPressListener;
    this.saveStateHandler = saveStateHandler;
    this.saveStateFileManager = saveStateFileManager;
  }

  @Override
  public boolean keyDown(int keycode) {
    // try to process as shortcut command etc
    boolean processed = processCommand(keycode);
    if (processed) {
      return true;
    }

    // try to process as game key
    KeyLabel keyLabel = toChip8KeyLabel(keycode);
    if (keyLabel != null) {
      chip8KeyPressListener.keyPressed(keyLabel);
      return true;
    }

    return false;
  }

  private boolean processCommand(int keycode) {
    switch (keycode) {
      case Keys.NUM_9:
        saveGameState();
        return true;
      case Keys.NUM_0:
        loadGameState();
        return true;
    }

    return false;
  }


  private void saveGameState() {
    byte[] saveState = saveStateHandler.createSaveState();
    saveStateFileManager.saveFile(saveState);
  }

  private void loadGameState() {
    byte[] saveState = saveStateFileManager.load();
    saveStateHandler.loadFromSaveState(saveState);
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
