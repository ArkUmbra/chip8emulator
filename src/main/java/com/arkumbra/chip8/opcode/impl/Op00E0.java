package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op00E0 implements OpCode {

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpOOEOClearScreen;
  }

  @Override
  public void execute(Integer rawCommand, Machine machine) {
    machine.getScreen().clearScreen();
  }
}
