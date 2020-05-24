package com.arkumbra.chip8.debug;

import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.MachineImpl;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.state.SaveStateHandler;

public class DebuggerImpl implements Debugger {

  private final MachineImpl machine;
  private final SaveStateHandler saveStateHandler;
  private CycleListener cycleListener;

  private boolean executionPaused;
  private boolean doSingleStep;

  private int cycleCount;


  public DebuggerImpl(MachineImpl machine, SaveStateHandler saveStateHandler) {
    this.machine = machine;
    this.saveStateHandler = saveStateHandler;
  }

  @Override
  public byte[] getRam() {
    return machine.getRam().serialize();
  }

  @Override
  public char getRegisterValue(RegisterLabel registerLabel) {
    return machine.getRegisters().getRegister(registerLabel).get();
  }

  @Override
  public int getIndexRegisterValue() {
    return machine.getIndexRegister().get();
  }

  @Override
  public boolean[][] getScreenPixels() {
    return machine.getScreenMemoryHandle().getPixels();
  }

  @Override
  public boolean isKeyPressed(KeyLabel keyLabel) {
    return machine.getKeys().isPressed(keyLabel);
  }

  @Override
  public void toggleFreezeExecution() {
    this.executionPaused = !executionPaused;
    this.doSingleStep = false;
  }

  @Override
  public void freeze() {
    this.executionPaused = true;
    this.doSingleStep = false;
  }

  @Override
  public void unfreeze() {
    this.executionPaused = false;
    this.doSingleStep = false;
  }

  @Override
  public void stepForward() {
    this.executionPaused = false;
    this.doSingleStep = true;
  }

  @Override
  public void stepBackward() {
    this.saveStateHandler.stepStateBackAndPause();
  }

  @Override
  public void setCycleListener(CycleListener cycleListener) {
    this.cycleListener = cycleListener;
  }

  public void blockUntilExecutionUnpaused() {
    if (doSingleStep) {
      this.doSingleStep = false;
      this.executionPaused = true;
    }

    while (true) {
      if (! executionPaused) {
        return;
      }

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  public void endOfCycle() {
    cycleCount++;

    if (cycleListener != null) {
      cycleListener.onSingleCycleCompleted();
    }
  }


}
