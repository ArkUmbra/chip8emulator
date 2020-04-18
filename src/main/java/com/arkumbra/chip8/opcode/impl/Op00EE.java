package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op00EE implements OpCode {

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op00EEReturn;
  }

  @Override
  public void execute(Integer rawCommand, Machine machine) {
    throw new UnsupportedOperationException("Opcode return should be just used as a flag");
  }
}
