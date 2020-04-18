package com.arkumbra.chip8.opcode.impl;


import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op00EETest {

  private Op00EE sut;

  @Before
  public void setup() {
    this.sut = new Op00EE();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testCantExecuteReturnOperation() {
    char opCode = 0xFFFF;
    char opCodeData = sut.getBitMask().applyMask(opCode);
    Machine machineMock = TestUtils.mockMachineAndParts();

    sut.execute(opCodeData, machineMock);
  }

}
