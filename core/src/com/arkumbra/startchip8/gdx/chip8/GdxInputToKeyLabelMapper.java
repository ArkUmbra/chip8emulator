package com.arkumbra.startchip8.gdx.chip8;

import com.arkumbra.chip8.machine.KeyLabel;
import com.badlogic.gdx.Input.Keys;

public class GdxInputToKeyLabelMapper {

  /**
   *   W E R    Y U I
   *   S D F    H J K
   * Z X C V
   *
   *   1 2 3    A B C
   *   4 5 6    D E F
   * 0 7 8 9
   * @param gdxInputKeyCode keyCode from keyboard input
   * @return
   */
  public static KeyLabel fromKeyboardButton(int gdxInputKeyCode) {
    switch (gdxInputKeyCode) {
      case Keys.W: return KeyLabel.K1;
      case Keys.E: return KeyLabel.K2;
      case Keys.R: return KeyLabel.K3;
      case Keys.S: return KeyLabel.K4;
      case Keys.D: return KeyLabel.K5;
      case Keys.F: return KeyLabel.K6;
      case Keys.X: return KeyLabel.K7;
      case Keys.C: return KeyLabel.K8;
      case Keys.V: return KeyLabel.K9;
      case Keys.Z: return KeyLabel.K0;

      case Keys.Y: return KeyLabel.KA;
      case Keys.U: return KeyLabel.KB;
      case Keys.I: return KeyLabel.KC;
      case Keys.H: return KeyLabel.KD;
      case Keys.J: return KeyLabel.KE;
      case Keys.K: return KeyLabel.KF;
    }

    return null;
  }

}
