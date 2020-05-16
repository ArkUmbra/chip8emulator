package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class Op5XY0Test {

  private Op5XY0 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op5XY0();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_whenVXDoesntEqualVY_thenDontSkipNextInstruction() {
    // B - registerX. D - registerY
    char inputOpCode = 0x5BD0;

    // different values in each register
    char valueInRegisterX = 0xA1;
    char valueInRegisterY = 0xA2;

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
    verify(machineMock, Mockito.never()).getProgramCounter();
  }

  @Test
  public void testExecute_whenVXDoesEqualVY_thenSkipNextInstruction() {
    // B - registerX. D - registerY
    char inputOpCode = 0x5BD0;

    // same values in each register
    char valueInRegisterX = 0xFA;
    char valueInRegisterY = 0xFA;

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
    verify(machineMock.getProgramCounter(), times(1)).skipNextInstruction();
  }

}
