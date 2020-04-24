package com.arkumbra.chip8;

import com.arkumbra.chip8.machine.Dumpable;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.MachineImpl;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.RoutineRunner;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import com.arkumbra.chip8.opcode.OpCodeLookup;
import com.arkumbra.chip8.opcode.OpCodeLookupImpl;
import java.io.IOException;

public class Chip8 implements RoutineRunner, Dumpable {

  private MachineImpl machine;
  private OpCodeLookup opCodeLookup = new OpCodeLookupImpl();

  public Chip8() {
    this.machine = new MachineImpl(this);
  }

  public void start(String gameFilePath) {

    GameLoader gameLoader = new GameLoader();
    try {
      Memory memory = gameLoader.loadGameIntoMemory(gameFilePath);
      machine.loadIntoMemory(memory);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   *
   * @return Executed opcode
   */
  @Override
  public OpCodeLabel runCycle() {
    Memory memory = machine.getMemory();
    ProgramCounter pc = machine.getProgramCounter();

    char rawOpCode = memory.readRawOpCode(pc);
    OpCode opCode = opCodeLookup.lookup(rawOpCode);
    char opData = opCode.getBitMask().applyMask(rawOpCode);

    // if op code for 'return' then don't execute
    OpCodeLabel opCodeLabel = opCode.getOpCodeLabel();
    if (opCodeLabel != OpCodeLabel.Op00EEReturn) {
      opCode.execute(opData, machine);
    }

    pc.increment();

    return opCodeLabel;
  }

  @Override
  public String dump() {
    return machine.dump();
  }
}
