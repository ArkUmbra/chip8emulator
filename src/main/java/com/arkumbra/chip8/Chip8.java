package com.arkumbra.chip8;

import com.arkumbra.chip8.external.JPanelOutputter;
import com.arkumbra.chip8.external.ScreenOutputter;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chip8 implements RoutineRunner, Dumpable {

  private MachineImpl machine;
  private OpCodeLookup opCodeLookup = new OpCodeLookupImpl();
  private ScreenOutputter screenOutputter = new JPanelOutputter();

  private LinkedList<String> commandExecutionOrder = new LinkedList<>();

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

    commandExecutionOrder.addLast(Integer.toHexString(rawOpCode) + " - " + pc.getPosition() + " - ");

    OpCode opCode = opCodeLookup.lookup(rawOpCode);
    char opData = opCode.getBitMask().applyMask(rawOpCode);

    commandExecutionOrder.addLast(commandExecutionOrder.removeLast()  + opCode.getOpCodeLabel() + " - " + Integer.toHexString(opData));

    // if op code for 'return' then don't execute
    OpCodeLabel opCodeLabel = opCode.getOpCodeLabel();
    if (opCodeLabel != OpCodeLabel.Op00EEReturn) {
      opCode.execute(opData, machine);
    }

    machine.tick();
    pc.increment();
    screenOutputter.drawFrame(machine.getScreenMemoryHandle());

    try {
      Thread.sleep(30);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return opCodeLabel;
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- Commands ---");
    sb.append(System.lineSeparator());
    sb.append(String.join(System.lineSeparator(), commandExecutionOrder));
    sb.append(System.lineSeparator());
    sb.append(machine.dump());
    return sb.toString();
  }
}
