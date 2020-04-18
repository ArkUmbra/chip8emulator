package com.arkumbra.chip8.opcode;


import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.opcode.impl.Op00EE;
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
    Machine machineMock = TestUtils.mockMachineAndParts();

    sut.execute(0x00EE, machineMock);
  }

}
