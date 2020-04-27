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

  private final Boolean LOCK = true;


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

    long lastKeyPressAtCheckStart;
    synchronized (LOCK) {
      lastKeyPressAtCheckStart = lastKeyPressTime;
    }

    while (true) {

      boolean keyPressed;
      KeyLabel lastPressed;
      synchronized (LOCK) {
        keyPressed = lastKeyPressTime != lastKeyPressAtCheckStart;
        lastPressed = lastKeyPressed;
      }

      if (keyPressed) {
        return lastPressed;
      } else {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void keyPressed(KeyLabel keyLabel) {
    keys.get(keyLabel).press(true);

    synchronized (LOCK) {
      lastKeyPressTime = System.currentTimeMillis();
      lastKeyPressed = keyLabel;
    }
  }

  @Override
  public void keyReleased(KeyLabel keyLabel) {
    keys.get(keyLabel).press(false);
  }

}
