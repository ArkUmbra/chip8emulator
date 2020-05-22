package com.arkumbra.chip8.machine;

import java.awt.Toolkit;

public class DefaultSoundOutputter implements SoundOutputter{

  @Override
  public void beep() {
    // TODO probably better to replace with an actual sound effects library, because this beep
    //  seems to have other side effects like auto-switching to workspace 1 on Mac...

    Toolkit.getDefaultToolkit().beep();
  }

}
