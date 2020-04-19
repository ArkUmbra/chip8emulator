package com.arkumbra.chip8;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.IndexRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.ProgramCounter;
import com.arkumbra.chip8.machine.Registers;
import com.arkumbra.chip8.machine.RoutineRunner;
import com.arkumbra.chip8.screen.Screen;

public class TestUtils {

  public static Machine mockMachineAndParts() {
    Machine machineMock = mock(Machine.class);

    ProgramCounter pc = mock(ProgramCounter.class);
    RoutineRunner rr = mock(RoutineRunner.class);
    Screen screen = mock(Screen.class);
    Registers registers = mock(Registers.class);
    IndexRegister indexRegister = mock(IndexRegister.class);

    when(machineMock.getProgramCounter()).thenReturn(pc);
    when(machineMock.getRoutineRunner()).thenReturn(rr);
    when(machineMock.getScreen()).thenReturn(screen);
    when(machineMock.getRegisters()).thenReturn(registers);
    when(machineMock.getIndexRegister()).thenReturn(indexRegister);

    return machineMock;
  }

}
