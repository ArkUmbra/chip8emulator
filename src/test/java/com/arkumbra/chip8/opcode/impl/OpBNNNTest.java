package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterKey;
import org.junit.Before;
import org.junit.Test;

public class OpBNNNTest {

  private OpBNNN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpBNNN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setPointerToNNNPlusV0() {
    char inputOpCode = 0xBBCD;

    char v0RegisterValue = 0xFFF;
    int expectedSetValue = 0xBCD + v0RegisterValue;

    DataRegister mockDataRegisterV0 = mock(DataRegister.class);
    when(mockDataRegisterV0.get()).thenReturn(v0RegisterValue);
    when(machineMock.getRegisters().getRegister(RegisterKey.V0))
        .thenReturn(mockDataRegisterV0);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getIndexRegister(), times(1))
        .set(expectedSetValue);
  }

}
