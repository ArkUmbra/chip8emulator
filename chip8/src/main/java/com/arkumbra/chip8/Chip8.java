package com.arkumbra.chip8;

import com.arkumbra.chip8.external.GuiService;
import com.arkumbra.chip8.machine.Dumpable;
import com.arkumbra.chip8.machine.MachineImpl;
import com.arkumbra.chip8.machine.Ram;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.external.SoundService;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLookup;
import com.arkumbra.chip8.opcode.OpCodeLookupImpl;
import com.arkumbra.chip8.state.SaveStateHandler;
import java.io.IOException;
import java.util.LinkedList;

public class Chip8 implements Dumpable {
  private final Logger logger = new Logger(getClass());

  private static final OpCodeLookup opCodeLookup = new OpCodeLookupImpl();

  private MachineImpl machine;
  private GuiService guiService;
  private SaveStateManager saveStateManager = new SaveStateManager();
  private Thread gameThread;



  public Chip8(GuiService guiService, SoundService soundService) {
    this.machine = new MachineImpl(soundService);
    this.guiService = guiService;
  }

  public void loadGame(String gameFilePath) {
    GameLoader gameLoader = new GameLoader();
    try {
      Ram ram = gameLoader.loadGameIntoMemory(gameFilePath);
      machine.loadIntoMemory(ram);

    } catch (IOException e) {
      e.printStackTrace();
    }

    guiService.init(machine.getScreenMemoryHandle(), machine.getKeys());
  }

  public void runAsync() {
    Runnable gameRunner = () -> {
      try {
          while (true) {
            runSingleCycle();
          }

      } catch (InterruptedException e) {
        logger.debug("Game thread was terminated. Possibly due to reloading game state");
        logger.debug(e.toString());
      }
    };

    gameThread = new Thread(gameRunner);
    gameThread.start();
  }

  /**
   *
   */
  private void runSingleCycle() throws InterruptedException {
    Ram ram = machine.getRam();
    ProgramCounter pc = machine.getProgramCounter();

    char rawOpCode = ram.readRawOpCode(pc);
    OpCode opCode = opCodeLookup.lookup(rawOpCode);

    char opData = opCode.getBitMask().applyMask(rawOpCode);
    opCode.execute(opData, machine);

    pc.increment();
    machine.tick();

    saveStateManager.recordState();

    // TODO add better clock speed control
    Thread.sleep(1);
  }

  @Override
  public String dump() {
    return machine.dump();
  }

  public SaveStateHandler getSaveStateHandler() {
    return saveStateManager;
  }



  // ===============================================================================================

  class SaveStateManager implements SaveStateHandler {
    private final LinkedList<byte[]> state = new LinkedList<>();

    @Override
    public byte[] createSaveState() {
      return state.getLast();
    }

    @Override
    public void loadFromSaveState(byte[] state) {
      this.state.clear();
      gameThread.interrupt();

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      machine.getProgramCounter().freeze();
      machine.loadFromSaveState(state);
      machine.getProgramCounter().unfreeze();

      runAsync();
    }

    public void recordState() {
      if (state.size() > 1000) {
        state.removeFirst();
      }

      state.addLast(machine.createSaveState());
    }
  }

}


