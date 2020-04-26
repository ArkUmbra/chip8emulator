package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

/**
 * Computers which used to run Chip8 had a hexidecimal keyboard layout as follows
 *  1	2	3	C
 *  4	5	6	D
 *  7	8	9	E
 *  A	0	B	F
 *
 * We will map it onto our keyboard as follows
 *  1 2 3 4
 *  Q W E R
 *  A S D R
 *  Z X C V
 *
 **/
public class KeysImpl implements Keys, KeyPressListener {

  private final Map<KeyLabel, KeyImpl> keys = new HashMap<>();

  // also use as synchronize lock
  private KeyLabel lastKeyPressed = null;
  private Long lastKeyPressTime = 0L;


  public KeysImpl() {
    for (KeyLabel keyLabel : KeyLabel.values()) {
      this.keys.put(keyLabel, new KeyImpl());
    }
  }

  @Override
  public Key getKey(KeyLabel keyLabel) {
    return keys.get(keyLabel);
  }

  @Override
  public KeyLabel waitForNextKeyPress() {



    while (true) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
//      synchronized (something) {
//         check
//         if key pressed
//         then return key label
//      }
//
//       sleep. tick.wait() or something??... maybe simple sleep is fine...
//
    }
//
//    long timeOfLastKeyPressAtStartOfWait =

    // TODO...
//    return null;
  }

  @Override
  public void keyPressed(KeyLabel keyLabel) {
    // TODO what about off?
    keys.get(keyLabel).press(true);
  }

  @Override
  public void keyReleased(KeyLabel keyLabel) {
    keys.get(keyLabel).press(false);
  }

}
