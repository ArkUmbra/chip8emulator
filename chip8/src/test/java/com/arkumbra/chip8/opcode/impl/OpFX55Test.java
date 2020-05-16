package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import org.junit.Before;
import org.junit.Test;

public class OpFX55Test {

  private OpFX55 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX55();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_dumpRegistersUpAndIncludingX() {
    char inputOpCode = 0xF755;
    char expectedNumberOfRegistersToDump = 8; // 0 - 7 INCLUSIVE
    char valueInEveryRegister = 1;

    DataRegister register = mock(DataRegister.class);
    when(register.get()).thenReturn(valueInEveryRegister);
    when(machineMock.getRegisters().getRegister(any()))
        .thenReturn(register);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);


    verify(machineMock.getRegisters(), times(expectedNumberOfRegistersToDump))
        .getRegister(any());
    verify(machineMock.getMemory(), times(1))
        .write(machineMock.getIndexRegister().get(), new byte[]{1, 1, 1, 1, 1, 1, 1, 1});
  }

}
