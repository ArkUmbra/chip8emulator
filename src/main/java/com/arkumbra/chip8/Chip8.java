package com.arkumbra.chip8;

import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import com.arkumbra.chip8.opcode.OpCodeLookup;

public class Chip8 implements RoutineRunner {

  private ProgramCounter programCounter = null;
  private Memory memory = null;
  private OpCodeLookup opCodeLookup = null;

  private Machine machine = null;

  public void init() {

  }

  /**
   *
   * @return Executed opcode
   */
  @Override
  public OpCodeLabel runCycle() {
    char rawOpCode = memory.readRawOpCode(programCounter);
    OpCode opCode = opCodeLookup.lookup(rawOpCode);
    char opData = opCode.getBitMask().applyMask(rawOpCode);

    // if op code for 'return' then don't execute
    OpCodeLabel opCodeLabel = opCode.getOpCodeLabel();
    if (opCodeLabel != OpCodeLabel.Op00EEReturn) {
      opCode.execute(opData, machine);
    }

    return opCodeLabel;
  }

}
