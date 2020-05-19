package com.arkumbra.chip8.external;

import com.arkumbra.chip8.machine.KeyLabel;

/**
 * Chip8 ran on computers that made use of hex keyboards, so we have to remap any modern input
 * to one of the old key values
 */
public class InputToKeyLabelMapper {


  /**
   *   W E R    Y U I
   *   S D F    H J K
   * Z X C V
   *
   *   1 2 3    A B C
   *   4 5 6    D E F
   * 0 7 8 9
   * @param inputButton
   * @return
   */
  public static KeyLabel fromKeyboardButton(char inputButton) {
    switch (inputButton) {
      case 'w': return KeyLabel.K1;
      case 'e': return KeyLabel.K2;
      case 'r': return KeyLabel.K3;
      case 's': return KeyLabel.K4;
      case 'd': return KeyLabel.K5;
      case 'f': return KeyLabel.K6;
      case 'x': return KeyLabel.K7;
      case 'c': return KeyLabel.K8;
      case 'v': return KeyLabel.K9;
      case 'z': return KeyLabel.K0;

      case 'y': return KeyLabel.KA;
      case 'u': return KeyLabel.KB;
      case 'i': return KeyLabel.KC;
      case 'h': return KeyLabel.KD;
      case 'j': return KeyLabel.KE;
      case 'k': return KeyLabel.KF;
    }

    return null;
  }

//  public static KeyLabel fromKeyCode(int keyCode) {
//    switch (keyCode) {
//      case 'w': return KeyLabel.K1;
//      case 'e': return KeyLabel.K2;
//      case 'r': return KeyLabel.K3;
//      case 's': return KeyLabel.K4;
//      case 'd': return KeyLabel.K5;
//      case 'f': return KeyLabel.K6;
//      case 'x': return KeyLabel.K7;
//      case 'c': return KeyLabel.K8;
//      case 'v': return KeyLabel.K9;
//      case 'z': return KeyLabel.K0;
//
//      case 'y': return KeyLabel.KA;
//      case 'u': return KeyLabel.KB;
//      case 'i': return KeyLabel.KC;
//      case 'h': return KeyLabel.KD;
//      case 'j': return KeyLabel.KE;
//      case 'k': return KeyLabel.KF;
//    }
//
//    return null;
//  }

}
