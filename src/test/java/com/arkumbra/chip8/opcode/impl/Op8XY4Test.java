package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.DataRegister;
import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.RadixUtils;
import com.arkumbra.chip8.RegisterKey;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class Op8XY4Test {

  private Op8XY4 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op8XY4();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testCarry_scrapbook() {
    char valueInRegisterX = 0x09;
    char valueInRegisterY = 0xFF;
    char over = 256;

    RadixUtils.printHexAndBinary((char)(over & 0xFF));

    RadixUtils.printHexAndBinary(Character.MAX_VALUE);
    RadixUtils.printHexAndBinary(valueInRegisterX);
    RadixUtils.printHexAndBinary(valueInRegisterY);
    System.out.println(valueInRegisterX + valueInRegisterY);
    System.out.println((int)(char)(valueInRegisterX + valueInRegisterY));
  }

  @Test
  public void testExecute_addVYtoVY_givenCarryThenSetVFToOne() {
    // B - registerX. D - registerY
    char inputOpCode = 0x8BD4;

    // different values in each register
    char valueInRegisterB = (char)1;
    char valueInRegisterD = (char)255;
    char expectedSet = 0; // 255 should roll over to 0 (255 + 1 becomes 0 if you mask with 0xFF)
    char expectedCarryFlag = 1;

    DataRegister mockDataRegisterB = mock(DataRegister.class);
    DataRegister mockDataRegisterD = mock(DataRegister.class);
    DataRegister mockDataRegisterF = mock(DataRegister.class);
    when(mockDataRegisterB.get()).thenReturn(valueInRegisterB);
    when(mockDataRegisterD.get()).thenReturn(valueInRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterKey.VB))
        .thenReturn(mockDataRegisterB);
    when(machineMock.getRegisters().getRegister(RegisterKey.VD))
        .thenReturn(mockDataRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterKey.VF))
        .thenReturn(mockDataRegisterF);
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterB, times(1)).get();
    verify(mockDataRegisterD, times(1)).get();
    verify(mockDataRegisterB, times(1)).set(expectedSet);
    verify(mockDataRegisterF, times(1)).set(expectedCarryFlag);

    verifyNoMoreInteractions(mockDataRegisterB, mockDataRegisterD, mockDataRegisterF);
  }

  @Test
  public void testExecute_addVYtoVY_givenCarryThenSetVFToOne_differentValues() {
    // B - registerX. D - registerY
    char inputOpCode = 0x8BD4;

    // different values in each register
    char valueInRegisterB = (char)10;
    char valueInRegisterD = (char)255;
    char expectedSet = 9; // 255 should roll over to 0 (255 + 1 becomes 0 if you mask with 0xFF)
    char expectedCarryFlag = 1;

    DataRegister mockDataRegisterB = mock(DataRegister.class);
    DataRegister mockDataRegisterD = mock(DataRegister.class);
    DataRegister mockDataRegisterF = mock(DataRegister.class);
    when(mockDataRegisterB.get()).thenReturn(valueInRegisterB);
    when(mockDataRegisterD.get()).thenReturn(valueInRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterKey.VB))
        .thenReturn(mockDataRegisterB);
    when(machineMock.getRegisters().getRegister(RegisterKey.VD))
        .thenReturn(mockDataRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterKey.VF))
        .thenReturn(mockDataRegisterF);
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterB, times(1)).get();
    verify(mockDataRegisterD, times(1)).get();
    verify(mockDataRegisterB, times(1)).set(expectedSet);
    verify(mockDataRegisterF, times(1)).set(expectedCarryFlag);

    verifyNoMoreInteractions(mockDataRegisterB, mockDataRegisterD, mockDataRegisterF);
  }

  @Test
  public void testExecute_addVYtoVY_givenNoCarryThenSetVFToZero() {
    // B - registerX. D - registerY
    char inputOpCode = 0x8BD4;

    // different values in each register
    char valueInRegisterB = (char)10;
    char valueInRegisterD = (char)245;
    char expectedSet = 255; // 255 should roll over to 0 (255 + 1 becomes 0 if you mask with 0xFF)
    char expectedCarryFlag = 0;

    DataRegister mockDataRegisterB = mock(DataRegister.class);
    DataRegister mockDataRegisterD = mock(DataRegister.class);
    DataRegister mockDataRegisterF = mock(DataRegister.class);
    when(mockDataRegisterB.get()).thenReturn(valueInRegisterB);
    when(mockDataRegisterD.get()).thenReturn(valueInRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterKey.VB))
        .thenReturn(mockDataRegisterB);
    when(machineMock.getRegisters().getRegister(RegisterKey.VD))
        .thenReturn(mockDataRegisterD);
    when(machineMock.getRegisters().getRegister(RegisterKey.VF))
        .thenReturn(mockDataRegisterF);
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegisterB, times(1)).get();
    verify(mockDataRegisterD, times(1)).get();
    verify(mockDataRegisterB, times(1)).set(expectedSet);
    verify(mockDataRegisterF, times(1)).set(expectedCarryFlag);

    verifyNoMoreInteractions(mockDataRegisterB, mockDataRegisterD, mockDataRegisterF);
  }


}
