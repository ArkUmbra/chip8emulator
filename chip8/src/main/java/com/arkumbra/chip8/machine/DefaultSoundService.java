package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.external.SoundService;
import java.awt.Toolkit;

public class DefaultSoundService implements SoundService {

  @Override
  public void beep() {
    // TODO probably better to replace with an actual sound effects library, because this beep
    //  seems to have other side effects like auto-switching to workspace 1 on Mac...

    Toolkit.getDefaultToolkit().beep();
  }

}
