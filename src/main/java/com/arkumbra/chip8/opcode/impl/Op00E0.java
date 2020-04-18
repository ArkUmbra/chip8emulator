package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op00E0 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.EMPTY;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpOOEOClearScreen;
  }

  @Override
  public void execute(char postMaskOpData, Machine machine) {
    machine.getScreen().clearScreen();
  }
}
