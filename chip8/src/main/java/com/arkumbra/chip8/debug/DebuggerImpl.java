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
    // TODO, should just manage this in here, rather than putting it in program counter...
    //  Let the emulator check at the end of each cycle
    machine.getProgramCounter().toggleFreezeExecution();
  }

  @Override
  public void freeze() {
    // TODO, should just manage this in here, rather than putting it in program counter...
    //  Let the emulator check at the end of each cycle
    machine.getProgramCounter().freeze();
  }

  @Override
  public void unfreeze() {
    // TODO, should just manage this in here, rather than putting it in program counter...
    //  Let the emulator check at the end of each cycle
    machine.getProgramCounter().unfreeze();
  }
}
