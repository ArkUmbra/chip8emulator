package com.arkumbra.chip8.external;


import com.arkumbra.chip8.debug.Debugger;
import com.arkumbra.chip8.machine.KeyPressListener;
import com.arkumbra.chip8.machine.ScreenMemory;
import com.arkumbra.chip8.state.SaveStateHandler;

public interface GuiService {


  /***
   * Callback once the CHIP-8 has been created, providing various services to allow
   * interaction with the emulator
   * @param screenMemory The current contents of the screen
   * @param keyPressListener Call this listener when the user presses a key
   * @param saveStateHandler Service to allow managing the game state
   * @param debugger
   */
  void init(ScreenMemory screenMemory, KeyPressListener keyPressListener,
      SaveStateHandler saveStateHandler, Debugger debugger);
}
