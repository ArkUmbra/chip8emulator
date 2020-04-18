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
    int rawOpCode = memory.readRawOpCode(programCounter);
    OpCode opCode = opCodeLookup.lookup(rawOpCode);

    // if op code for 'return' then don't execute
    OpCodeLabel opCodeLabel = opCode.getOpCodeLabel();
    if (opCodeLabel != OpCodeLabel.Op00EEReturn) {
      opCode.execute(rawOpCode, machine);
    }

    return opCodeLabel;
  }

}
