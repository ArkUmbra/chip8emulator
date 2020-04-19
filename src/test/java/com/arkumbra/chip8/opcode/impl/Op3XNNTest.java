package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterKey;
import com.arkumbra.chip8.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class Op3XNNTest {

  private Op3XNN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op3XNN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_whenVXDoesntEqualNN_thenDontSkipNextInstruction() {
    // F - register. 77 data value
    char inputOpCode = 0x3F77;

    // different to check value 0x77
    char valueInRegister = 0x76;

    DataRegister mockDataRegister = mock(DataRegister.class);
    when(mockDataRegister.get()).thenReturn(valueInRegister);
    when(machineMock.getRegisters().getRegister(RegisterKey.VF))
        .thenReturn(mockDataRegister);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegister, times(1)).get();
    verify(machineMock, Mockito.never()).getProgramCounter();
  }

  @Test
  public void testExecute_whenVXDoesEqualNN_thenSkipNextInstruction() {
    // F - register. 77 data value
    char inputOpCode = 0x3F77;

    // Same as check value 0x77
    char valueInRegister = 0x77;

    DataRegister mockDataRegister = mock(DataRegister.class);
    when(mockDataRegister.get()).thenReturn(valueInRegister);
    when(machineMock.getRegisters().getRegister(RegisterKey.VF))
        .thenReturn(mockDataRegister);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(mockDataRegister, times(1)).get();
    verify(machineMock, times(1)).getProgramCounter();
    verify(machineMock.getProgramCounter(), times(1)).increment();
  }

}
