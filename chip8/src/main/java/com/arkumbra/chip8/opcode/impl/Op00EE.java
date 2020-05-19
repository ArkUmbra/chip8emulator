package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op00EE implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.TWO_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op00EEReturn;
  }

  @Override
  public void execute(char postMaskOpData, Machine machine) {
    throw new UnsupportedOperationException("Opcode return should be just used as a flag");
  }
}
