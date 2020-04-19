package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.Machine;
import org.junit.Before;
import org.junit.Test;

public class OpANNNTest {

  private OpANNN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpANNN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_whenVXDoesntEqualVY_thenSkipNextInstruction() {
    char inputOpCode = 0xABCD;
    char expectedSetValue = 0xBCD;

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getIndexRegister(), times(1))
        .set(expectedSetValue);
  }

}
