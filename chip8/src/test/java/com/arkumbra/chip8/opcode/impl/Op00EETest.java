package com.arkumbra.chip8.opcode.impl;


import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op00EETest {

  private Op00EE sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op00EE();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testReturnsFromPreviousSubroutine() throws InterruptedException {
    char inputOpCode = 0xABCF;

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter(), times(1)).pop();
  }

}
