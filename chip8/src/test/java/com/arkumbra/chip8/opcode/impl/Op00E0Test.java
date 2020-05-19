package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op00E0Test {

  private Op00E0 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op00E0();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testClearsScreen() {
    char opCode = 0x00E0;
    char opCodeData = sut.getBitMask().applyMask(opCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getScreen(), times(1)).clearScreen();
  }

}
