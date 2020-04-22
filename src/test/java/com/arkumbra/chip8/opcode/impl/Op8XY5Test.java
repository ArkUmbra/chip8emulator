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

public class Op8XY5Test {

  private Op8XY5 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op8XY5();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_subVYfromVX_givenBorrowThenSetVFToZero() {
    // B - registerX. D - registerY
    char inputOpCode = 0x8BD4;

    // different values in each register
    char valueInRegisterB = (char)2;
    char valueInRegisterD = (char)5;
    char expectedSet = 253; // 2 should roll over to 65533, then mask to 253
    char expectedBorrowFlag = 0;

    DataRegister mockDataRegisterB = mock(DataRegister.class);
    DataRegister mockDataRegisterD = mock(DataRegister.class);
    DataRegister mockDataRegisterF = mock(DataRegister.class);
    when(mockDataRegisterB.get()).thenReturn(valueInRegisterB);
    when(mockDataRegisterD.get()).thenReturn(valueInRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VB))
        .thenReturn(mockDataRegisterB);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VD))
        .thenReturn(mockDataRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VF))
        .thenReturn(mockDataRegisterF);
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterB, times(1)).get();
    verify(mockDataRegisterD, times(1)).get();
    verify(mockDataRegisterB, times(1)).set(expectedSet);
    verify(mockDataRegisterF, times(1)).set(expectedBorrowFlag);

    verifyNoMoreInteractions(mockDataRegisterB, mockDataRegisterD, mockDataRegisterF);
  }

  @Test
  public void testExecute_subVYfromVX_givenNoBorrowThenSetVFToOne() {
    // B - registerX. D - registerY
    char inputOpCode = 0x8BD4;

    // different values in each register
    char valueInRegisterB = (char)2;
    char valueInRegisterD = (char)2;
    char expectedSet = 0;
    char expectedBorrowFlag = 1;

    DataRegister mockDataRegisterB = mock(DataRegister.class);
    DataRegister mockDataRegisterD = mock(DataRegister.class);
    DataRegister mockDataRegisterF = mock(DataRegister.class);
    when(mockDataRegisterB.get()).thenReturn(valueInRegisterB);
    when(mockDataRegisterD.get()).thenReturn(valueInRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VB))
        .thenReturn(mockDataRegisterB);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VD))
        .thenReturn(mockDataRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VF))
        .thenReturn(mockDataRegisterF);
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterB, times(1)).get();
    verify(mockDataRegisterD, times(1)).get();
    verify(mockDataRegisterB, times(1)).set(expectedSet);
    verify(mockDataRegisterF, times(1)).set(expectedBorrowFlag);

    verifyNoMoreInteractions(mockDataRegisterB, mockDataRegisterD, mockDataRegisterF);
  }


}
