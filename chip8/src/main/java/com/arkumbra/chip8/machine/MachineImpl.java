package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.Memory;
import com.arkumbra.chip8.MemoryImpl;
import com.arkumbra.chip8.SaveStateHandler;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MachineImpl implements Machine, SaveStateHandler, Dumpable {

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

  // default
  public MachineImpl(RoutineRunner routineRunner, SoundOutputter soundOutputter) {
    this.routineRunner = routineRunner;
    this.screen = new ScreenImpl();
    this.programCounter = new ProgramCounterImpl();
    this.registers = new DataRegistersImpl();
    this.indexRegister = new IndexRegisterImpl();
    this.keys = new KeysImpl();
    this.delayTimer = new DelayTimer();
    this.soundTimer = new SoundTimer(soundOutputter);
    this.font = new FontImpl();
  }

  // For testing, or if you really want to override everything
  public MachineImpl(RoutineRunner routineRunner, ScreenImpl screen,
      ProgramCounterImpl programCounter, DataRegistersImpl registers,
      IndexRegister indexRegister, KeysImpl keys, Timer delayTimer,
      Timer soundTimer, Font font) {
    this.routineRunner = routineRunner;
    this.screen = screen;
    this.programCounter = programCounter;
    this.registers = registers;
    this.indexRegister = indexRegister;
    this.keys = keys;
    this.delayTimer = delayTimer;
    this.soundTimer = soundTimer;
    this.font = font;
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

  @Override
  public byte[] createSaveState() {
    byte[] screenData = screen.serialize();
    byte[] registerData = registers.serialize();
    byte[] memoryData = memory.serialize();
    byte[] programCounterData = programCounter.serialize();
    byte[] delayTimerData = delayTimer.serialize();
    byte[] soundTimerData = soundTimer.serialize();

    ByteBuffer saveState = ByteBuffer.allocate(
        screenData.length + registerData.length + memoryData.length + programCounterData.length
            + delayTimerData.length + soundTimerData.length);

    saveState.put(screenData);
    saveState.put(registerData);
    saveState.put(memoryData);
    saveState.put(delayTimerData);
    saveState.put(soundTimerData);
    saveState.put(programCounterData);

    return saveState.array();
  }

  @Override
  public void loadFromSaveState(byte[] state) {

    int screenDataSize = ScreenImpl.SERIALIZED_LENGTH;
    int registerDataSize = DataRegistersImpl.SERIALIZED_LENGTH;
    int memoryDataSize = MemoryImpl.SERIALIZED_LENGTH;
    int delayTimerSize = BaseTimer.SERIALIZED_LENGTH;
    int soundTimerSize = BaseTimer.SERIALIZED_LENGTH;


    int endOfScreenDataIndex = screenDataSize;
    int endOfRegisterIndex = endOfScreenDataIndex + registerDataSize;
    int endOfMemoryIndex = endOfRegisterIndex + memoryDataSize;
    int endOfDelayIndex = endOfMemoryIndex + delayTimerSize;
    int endOfSoundIndex = endOfDelayIndex + soundTimerSize;
    int endOfProgramCounterIndex = state.length;

    byte[] screenData = Arrays.copyOfRange(state, 0, endOfScreenDataIndex);
    byte[] registerData = Arrays.copyOfRange(state, endOfScreenDataIndex, endOfRegisterIndex);
    byte[] memoryData = Arrays.copyOfRange(state, endOfRegisterIndex, endOfMemoryIndex);
    byte[] delayData = Arrays.copyOfRange(state, endOfMemoryIndex, endOfDelayIndex);
    byte[] soundData = Arrays.copyOfRange(state, endOfDelayIndex, endOfSoundIndex);
    byte[] programCounterData = Arrays.copyOfRange(state, endOfSoundIndex, endOfProgramCounterIndex);

    this.screen.deserialize(screenData);
    this.registers.deserialize(registerData);
    this.memory.deserialize(memoryData);
    this.delayTimer.deserialize(delayData);
    this.soundTimer.deserialize(soundData);
    this.programCounter.deserialize(programCounterData);
  }

}
