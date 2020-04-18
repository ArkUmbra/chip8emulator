package com.arkumbra.chip8.opcode;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.opcode.impl.Op00E0;
import com.arkumbra.chip8.screen.Screen;
import org.junit.Before;
import org.junit.Test;

public class Op00E0Test {

  private Op00E0 sut;

  @Before
  public void setup() {
    this.sut = new Op00E0();
  }

  @Test
  public void testClearsScreen() {
    Machine machineMock = TestUtils.mockMachineAndParts();

    sut.execute(0x00E0, machineMock);

    verify(machineMock.getScreen(), times(1)).clearScreen();
  }

}
