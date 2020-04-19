package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterKey;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpBNNN implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpBNNNJump;
  }

  @Override
  public void execute(char memoryAddress, Machine machine) {
    DataRegister v0Register = machine.getRegisters().getRegister(RegisterKey.V0);
    int newSetValue = memoryAddress + v0Register.get();
    machine.getIndexRegister().set(newSetValue);
  }

}
