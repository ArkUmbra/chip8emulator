package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.DataRegister;
import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.RegisterKey;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op7XNNTest {

  private Op7XNN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op7XNN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setDataNNToRegisterX() {
    // Register - 2. Data 9F
    char inputOpCode = 0x629F;
    char expectedRegisterSetValue = 0x9F;

    DataRegister mockDataRegisterX = mock(DataRegister.class);

    when(machineMock.getRegisters().getRegister(RegisterKey.V2))
        .thenReturn(mockDataRegisterX);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterX, times(1))
        .add(expectedRegisterSetValue);
    verifyNoMoreInteractions(mockDataRegisterX);
  }


}
