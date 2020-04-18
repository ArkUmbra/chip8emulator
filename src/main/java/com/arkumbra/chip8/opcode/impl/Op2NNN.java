package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.ProgramCounter;
import com.arkumbra.chip8.RoutineRunner;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op2NNN implements OpCode {

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op2NNNCallSubroutine;
  }

  @Override
  public void execute(Integer rawCommand, Machine machine) {
    short address = getAddressToUse(rawCommand);
    ProgramCounter programCounter = machine.getProgramCounter();
    RoutineRunner routineRunner = machine.getRoutineRunner();

    // Push the starting address of the subroutine onto the stack
    programCounter.push(address);

    // Loop until we hit a return
    OpCodeLabel lastCode;
    do {
      lastCode = routineRunner.runCycle();
    } while (lastCode != OpCodeLabel.Op00EEReturn);

    // Clean our subroutine pointer off the stack
    programCounter.pop();
  }

  private Short getAddressToUse(Integer rawCommand) {
    // TODO
    return -1;
  }
}
