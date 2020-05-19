package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Memory;

public class MachineImpl implements Machine, Dumpable {

  private final RoutineRunner routineRunner;
  private final ScreenImpl screen;
  private final ProgramCounterImpl programCounter;
  private final DataRegistersImpl registers;
  private final IndexRegister indexRegister;
  private final KeysImpl keys;
  private final Timer delayTimer;
  private final Timer soundTimer;
  private final Font font;
  private Memory memory;


  public MachineImpl(RoutineRunner routineRunner, SoundOutputter soundOutputter) {
    this.routineRunner = routineRunner;
    this.screen = new ScreenImpl();
    this.programCounter = new ProgramCounterImpl();
    this.registers = new DataRegistersImpl();
    this.indexRegister = new IndexRegisterImpl();
    this.keys = new KeysImpl();
    this.delayTimer = new DelayTimer();
    // TODO
    this.soundTimer = new SoundTimer(soundOutputter);
    this.font = new FontImpl();
  }

  @Override
  public void loadIntoMemory(Memory memory) {
    memory.load(font);
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
  public Font getFont() {
    return font;
  }

  @Override
  public void tick() {
    delayTimer.tick();
    soundTimer.tick();
  }

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();

    sb.append(screen.dump());
    sb.append(memory.dump());
    sb.append(registers.dump());
    return sb.toString();
  }
}
