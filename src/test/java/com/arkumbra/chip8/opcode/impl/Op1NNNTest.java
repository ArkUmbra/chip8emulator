package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op1NNNTest {

  private Op1NNN sut;

  @Before
  public void setup() {
    this.sut = new Op1NNN();
  }

  @Test
  public void testExecute() {
    char expectedGoTo = 0xBCD;
    char opCodeAndData = 0x1BCD;

    char opCodeData = sut.getBitMask().applyMask(opCodeAndData);

    Machine machineMock = TestUtils.mockMachineAndParts();

    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter()).goTo(expectedGoTo);
  }

}
