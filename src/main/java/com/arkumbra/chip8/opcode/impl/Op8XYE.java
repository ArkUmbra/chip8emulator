package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op8XYE implements OpCode {

  @Override
  public BitMask getBitMask() {
    throw new UnsupportedOperationException("Undocumented op code");
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    throw new UnsupportedOperationException("Undocumented op code");
  }

  @Override
  public void execute(char postMaskOpData, Machine machine) {
    throw new UnsupportedOperationException("Undocumented op code");
  }
}
