package com.arkumbra.chip8;

import com.arkumbra.chip8.external.DebugPanel;
import com.arkumbra.chip8.external.ScreenOutputter;
import com.arkumbra.chip8.machine.Dumpable;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.MachineImpl;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.RoutineRunner;
import com.arkumbra.chip8.machine.SoundOutputter;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import com.arkumbra.chip8.opcode.OpCodeLookup;
import com.arkumbra.chip8.opcode.OpCodeLookupImpl;
import java.io.IOException;
import java.util.LinkedList;

public class Chip8 implements RoutineRunner, SaveStateHandler, Dumpable {
  private final Logger logger = new Logger(getClass());

  private static final OpCodeLookup opCodeLookup = new OpCodeLookupImpl();

  private MachineImpl machine;
  private ScreenOutputter screenOutputter;
  private DebugPanel debugPanel;

  private LinkedList<String> commandExecutionOrder = new LinkedList<>();

  public Chip8(ScreenOutputter screenOutputter, SoundOutputter soundOutputter) {
    this.machine = new MachineImpl(this, soundOutputter);
    this.screenOutputter = screenOutputter;

    this.debugPanel = new DebugPanel(
        machine.getRegisters(),
        machine.getProgramCounter(),
        machine.getSoundTimer(),
        machine.getDelayTimer()
    );
  }

  public void loadGame(String gameFilePath) {

    GameLoader gameLoader = new GameLoader();
    try {
      Memory memory = gameLoader.loadGameIntoMemory(gameFilePath);
      machine.loadIntoMemory(memory);

    } catch (IOException e) {
      e.printStackTrace();
    }

    screenOutputter.init(machine.getScreenMemoryHandle(), machine.getKeys());
  }

  public void runAsync() {
    Runnable gameRunner = () -> {
      try {
          while (true) {
            runSingleCycle();
          }

      } catch (InterruptedException e) {
        System.err.println("Game thread was terminated. Exiting");
        e.printStackTrace();
      }
    };

    Thread gameThread = new Thread(gameRunner);
    gameThread.start();
  }

  /**
   *
   * @return Executed opcode
   */
  @Override
  public OpCodeLabel runSingleCycle() throws InterruptedException {
    Memory memory = machine.getMemory();
    ProgramCounter pc = machine.getProgramCounter();

    char rawOpCode = memory.readRawOpCode(pc);

    OpCode opCode = opCodeLookup.lookup(rawOpCode);
    OpCodeLabel opCodeLabel = opCode.getOpCodeLabel();

    char opData = opCode.getBitMask().applyMask(rawOpCode);
    opCode.execute(opData, machine);

    pc.increment();
    machine.tick();
    debugPanel.tick();

    // TODO add better clock speed control
    Thread.sleep(1);

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

  @Override
  public byte[] createSaveState() {
    machine.getProgramCounter().freeze();
    byte[] saveState = machine.createSaveState();

    machine.getProgramCounter().unfreeze();
    return saveState;
  }

  @Override
  public void loadFromSaveState(byte[] state) {
    machine.getProgramCounter().freeze();
    machine.loadFromSaveState(state);
    machine.getProgramCounter().unfreeze();
  }
}
