package com.arkumbra.chip8.debug;

import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.MachineImpl;
import com.arkumbra.chip8.machine.RegisterLabel;

public class DebuggerImpl implements Debugger {

  private final MachineImpl machine;

  public DebuggerImpl(MachineImpl machine) {
    this.machine = machine;
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
    machine.getProgramCounter().toggleFreezeExecution();
  }

  @Override
  public void freeze() {
    machine.getProgramCounter().freeze();
  }

  @Override
  public void unfreeze() {
    machine.getProgramCounter().unfreeze();
  }
}
