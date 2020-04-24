package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Memory;

public class MachineImpl implements Machine, KeyPressListener, Dumpable {

  private final RoutineRunner routineRunner;
  private final ScreenImpl screen;
  private final ProgramCounterImpl programCounter;
  private final DataRegisters registers;
  private final IndexRegister indexRegister;
  private final KeysImpl keys;
  private final Timer delayTimer;
  private final Timer soundTimer;
  private Memory memory;

  public MachineImpl(RoutineRunner routineRunner) {
    this.routineRunner = routineRunner;
    this.screen = new ScreenImpl();
    this.programCounter = new ProgramCounterImpl();
    this.registers = new DataRegistersImpl();
    this.indexRegister = new IndexRegisterImpl();
    this.keys = new KeysImpl();
    this.delayTimer = new DelayTimer();
    // TODO
    this.soundTimer = new SoundTimer(null);
  }

  @Override
  public void loadIntoMemory(Memory memory) {
    this.memory = memory;
  }

  public ScreenMemory getScreenMemoryHandle() {
    return screen;
  }

  @Override
  public RoutineRunner getRoutineRunner() {
    return routineRunner;
  }

  @Override
  public Screen getScreen() {
    return screen;
  }

  @Override
  public ProgramCounter getProgramCounter() {
    return programCounter;
  }

  @Override
  public DataRegisters getRegisters() {
    return registers;
  }

  @Override
  public IndexRegister getIndexRegister() {
    return indexRegister;
  }

  @Override
  public Memory getMemory() {
    return memory;
  }

  @Override
  public Keys getKeys() {
    return keys;
  }

  @Override
  public Timer getDelayTimer() {
    return delayTimer;
  }

  @Override
  public Timer getSoundTimer() {
    return soundTimer;
  }

  @Override
  public void tick() {
    delayTimer.tick();
    soundTimer.tick();
  }

  @Override
  public void keyPressed(KeyLabel keyLabel) {
    keys.keyPressed(keyLabel);
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();

    sb.append(screen.dump());
    sb.append(memory.dump());
    return sb.toString();
  }
}
