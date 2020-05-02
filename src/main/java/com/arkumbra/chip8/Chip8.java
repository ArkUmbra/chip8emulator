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
import java.util.LinkedList;
import java.util.Set;

public class Chip8 implements RoutineRunner, Dumpable {
  private final Logger logger = new Logger(getClass());

  private static final Set<OpCodeLabel> DEBUG_DUMP_BEFORE_EXECUTE = Set.of(OpCodeLabel.OpDXYNDraw);
  private static final Set<OpCodeLabel> DEBUG_DUMP_AFTER_EXECUTE = Set.of(OpCodeLabel.OpDXYNDraw);


  private static final OpCodeLookup opCodeLookup = new OpCodeLookupImpl();

  private MachineImpl machine;
  private ScreenOutputter screenOutputter;

  private LinkedList<String> commandExecutionOrder = new LinkedList<>();

  public Chip8() {
    this.machine = new MachineImpl(this);
    this.screenOutputter = new JPanelOutputter(machine.getKeys());
  }

  public void start(String gameFilePath) {

    GameLoader gameLoader = new GameLoader();
    try {
      Memory memory = gameLoader.loadGameIntoMemory(gameFilePath);
      machine.loadIntoMemory(memory);

    } catch (IOException e) {
      e.printStackTrace();
    }

//    logger.debug(dump());
    screenOutputter.init(machine.getScreenMemoryHandle());
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
    OpCodeLabel opCodeLabel = opCode.getOpCodeLabel();

//    if (DEBUG_DUMP_AFTER_EXECUTE.contains(opCodeLabel)) {
//      System.out.println(dump());
//    }


    char opData = opCode.getBitMask().applyMask(rawOpCode);
    commandExecutionOrder.addLast(commandExecutionOrder.removeLast()  + opCode.getOpCodeLabel() + " - " + Integer.toHexString(opData));

    // if op code for 'return' then don't execute
    if (opCodeLabel == OpCodeLabel.Op00EEReturn) {
      return opCodeLabel;
    }

//    if (DEBUG_DUMP_AFTER_EXECUTE.contains(opCodeLabel)) {
//      System.out.println(dump());
//    }

    opCode.execute(opData, machine);

    pc.increment();
    machine.tick();

    try {
      Thread.sleep(1);
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

  public Machine getMachine() {
    return machine;
  }
}
