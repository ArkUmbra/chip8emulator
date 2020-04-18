package com.arkumbra.chip8;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.screen.Screen;

public class TestUtils {

  public static Machine mockMachineAndParts() {
    Machine machineMock = mock(Machine.class);

    ProgramCounter pc = mock(ProgramCounter.class);
    RoutineRunner rr = mock(RoutineRunner.class);
    Screen screen = mock(Screen.class);

    when(machineMock.getProgramCounter()).thenReturn(pc);
    when(machineMock.getRoutineRunner()).thenReturn(rr);
    when(machineMock.getScreen()).thenReturn(screen);

    return machineMock;
  }

}
