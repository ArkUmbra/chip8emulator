package com.arkumbra.chip8.machine;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.arkumbra.chip8.external.SoundService;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class MachineSaveStateTest {

  private MachineImpl machine;

//  private RoutineRunner routineRunner;
//  private SoundOutputter soundOutputter;
  private ScreenImpl screenSpy;
  private ProgramCounterImpl programCounterSpy;
  private DataRegistersImpl registersSpy;
  private IndexRegister indexRegisterSpy;
  private KeysImpl keys;
  private Timer delayTimer;
  private Timer soundTimer;
  private Font font;

  @Before
  public void before() {
//    this.routineRunner = mock(RoutineRunner.class);
//    this.soundOutputter = mock(SoundOutputter.class);

    this.screenSpy = spy(new ScreenImpl());
    this.programCounterSpy = spy(new ProgramCounterImpl());
    this.registersSpy = spy(new DataRegistersImpl());
    this.indexRegisterSpy = spy(new IndexRegisterImpl());
//    this.keysSpy = spy(new KeysImpl());
    this.font = new FontImpl();
    this.keys = mock(KeysImpl.class);
    this.delayTimer= new DelayTimer();
    this.soundTimer = new SoundTimer(mock(SoundService.class));

    this.machine = new MachineImpl(routineRunner, screenSpy, programCounterSpy, registersSpy,
        indexRegisterSpy, keys, delayTimer, soundTimer, font);
  }

  @Test
  public void testSaveLoadSaveStateAll() {
    // Load game
    byte[] gameData = generateRandomGameData();
    Ram ram = new RamImpl();
    ram.load(gameData);
    machine.loadIntoMemory(ram);

    // Draw some random stuff to screen
    byte bit1 = 0b01011101;
    screenSpy.draw(bit1, 10, 16);
    byte bit2 = 0b01101000;
    screenSpy.draw(bit2, 63, 31);

    // Put data in some registers
    registersSpy.getRegister(RegisterLabel.V0).set((char) 56);
    registersSpy.getRegister(RegisterLabel.V4).set((char) 1);
    registersSpy.getRegister(RegisterLabel.VF).set((char) 123);

    // random program counter changes
    programCounterSpy.goTo(13);
    programCounterSpy.increment();
    programCounterSpy.push(123);
    programCounterSpy.increment();
    programCounterSpy.skipNextInstruction();

    byte[] saveState = machine.createSaveState();
    machine.loadFromSaveState(saveState);

    assertArrayEquals(saveState, machine.createSaveState());
  }

  @Test
  public void testSaveLoad_memoryOnly() {
    // Load game
    byte[] gameData = generateRandomGameData();
    Ram ram = new RamImpl();
    ram.load(gameData);

    byte[] memoryState = ram.serialize();
    ram.deserialize(memoryState);

    assertArrayEquals(memoryState, ram.serialize());
  }

  @Test
  public void testSaveLoad_screenOnly() {
    // Draw some random stuff to screen
    byte bit1 = 0b01010101;
    screenSpy.draw(bit1, 10, 16);
    byte bit2 = 0b01101111;
    screenSpy.draw(bit2, 63, 31);

    byte[] screenState = screenSpy.serialize();
    screenSpy.deserialize(screenState);

    assertArrayEquals(screenState, screenSpy.serialize());
  }

  private byte[] generateRandomGameData() {
    Random random = new Random();

    int length = random.nextInt(500);
    byte[] gameData = new byte[length];

    // Fill in with random stuff
    random.nextBytes(gameData);

    return gameData;
  }

  @Test
  public void testSaveLoad_registersOnly() {
    registersSpy.getRegister(RegisterLabel.V0).set((char) 7);
    registersSpy.getRegister(RegisterLabel.V9).set((char) 1);
    registersSpy.getRegister(RegisterLabel.VF).set((char) 99);

    byte[] serialised = registersSpy.serialize();
    registersSpy.deserialize(serialised);

    assertArrayEquals(serialised, registersSpy.serialize());

    assertEquals((char)7, registersSpy.getRegister(RegisterLabel.V0).get());
    assertEquals((char)1, registersSpy.getRegister(RegisterLabel.V9).get());
    assertEquals((char)99, registersSpy.getRegister(RegisterLabel.VF).get());
  }

  @Test
  public void testSaveLoad_programCounterOnly() {
    programCounterSpy.goTo(13);
    programCounterSpy.increment();
    programCounterSpy.push(123);
    programCounterSpy.increment();
    programCounterSpy.skipNextInstruction();

    byte[] serialized = programCounterSpy.serialize();
    programCounterSpy.deserialize(serialized);

    assertArrayEquals(serialized, programCounterSpy.serialize());
  }

}
