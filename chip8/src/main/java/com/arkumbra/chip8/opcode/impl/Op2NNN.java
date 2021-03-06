package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Logger;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op2NNN implements OpCode {
  private final Logger logger = new Logger(getClass());

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op2NNNCallSubroutine;
  }

  @Override
  public void execute(char subroutineAddress, Machine machine) {
    ProgramCounter programCounter = machine.getProgramCounter();

    // Push the starting address of the subroutine onto the stack
    programCounter.push(subroutineAddress);
  }

}
