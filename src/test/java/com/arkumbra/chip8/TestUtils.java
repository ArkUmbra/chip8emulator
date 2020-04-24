package com.arkumbra.chip8;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.IndexRegister;
import com.arkumbra.chip8.machine.Keys;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.DataRegisters;
import com.arkumbra.chip8.machine.RoutineRunner;
import com.arkumbra.chip8.machine.Screen;
import com.arkumbra.chip8.machine.Timer;
import com.arkumbra.chip8.machine.Timers;

public class TestUtils {

  public static Machine mockMachineAndParts() {
    Machine machineMock = mock(Machine.class);

    ProgramCounter pc = mock(ProgramCounter.class);
    RoutineRunner rr = mock(RoutineRunner.class);
    Screen screen = mock(Screen.class);
    DataRegisters registers = mock(DataRegisters.class);
    IndexRegister indexRegister = mock(IndexRegister.class);
    Memory memory = mock(Memory.class);
    Keys keys = mock(Keys.class);
    Timer delayTimer = mock(Timer.class);
    Timer soundTimer = mock(Timer.class);

    when(machineMock.getProgramCounter()).thenReturn(pc);
    when(machineMock.getRoutineRunner()).thenReturn(rr);
    when(machineMock.getScreen()).thenReturn(screen);
    when(machineMock.getRegisters()).thenReturn(registers);
    when(machineMock.getIndexRegister()).thenReturn(indexRegister);
    when(machineMock.getMemory()).thenReturn(memory);
    when(machineMock.getKeys()).thenReturn(keys);
    when(machineMock.getDelayTimer()).thenReturn(delayTimer);
    when(machineMock.getSoundTimer()).thenReturn(soundTimer);

    return machineMock;
  }

}
