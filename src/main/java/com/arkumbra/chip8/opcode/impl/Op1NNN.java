package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op1NNN implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op1NNNGoto;
  }

  @Override
  public void execute(char gotoAddress, Machine machine) {
    machine.getProgramCounter().goTo(gotoAddress);
  }


}
