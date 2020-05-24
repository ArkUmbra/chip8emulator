package com.arkumbra.chip8.debug;

import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.RegisterLabel;

public interface Debugger {

  byte[] getRam();
  char getRegisterValue(RegisterLabel registerLabel);
  int getIndexRegisterValue();
  boolean[][] getScreenPixels();
  boolean isKeyPressed(KeyLabel keyLabel);

  void toggleFreezeExecution();
  void freeze();
  void unfreeze();

  void stepForward();
  void stepBackward();

  void setCycleListener(CycleListener cycleListener);

}
