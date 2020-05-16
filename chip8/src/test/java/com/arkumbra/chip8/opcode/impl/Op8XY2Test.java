package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op8XY2Test {

  private Op8XY2 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op8XY2();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_whenAndRegisterYToRegisterX() {
    // B - registerX. D - registerY
    char inputOpCode = 0x8BD2;

    // different values in each register
    char valueInRegisterX = 0x12;
    char valueInRegisterY = 0xFD;
    char expectedSet = (char)(valueInRegisterX & valueInRegisterY);

    DataRegister mockDataRegisterX = mock(DataRegister.class);
    DataRegister mockDataRegisterY = mock(DataRegister.class);
    when(mockDataRegisterX.get()).thenReturn(valueInRegisterX);
    when(mockDataRegisterY.get()).thenReturn(valueInRegisterY);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VB))
        .thenReturn(mockDataRegisterX);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VD))
        .thenReturn(mockDataRegisterY);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterX, times(1)).get();
    verify(mockDataRegisterY, times(1)).get();
    verify(mockDataRegisterX, times(1)).set(expectedSet);

    verifyNoMoreInteractions(mockDataRegisterX, mockDataRegisterY);
  }

}
