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
    }

    return null;
  }

}
