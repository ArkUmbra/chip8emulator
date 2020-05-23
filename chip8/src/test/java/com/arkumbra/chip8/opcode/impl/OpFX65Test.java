package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import org.junit.Before;
import org.junit.Test;

public class OpFX65Test {

  private OpFX65 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX65();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_loadFromMemoryUpToAndIncludingRegisterX() {
    char inputOpCode = 0xF755;
    char expectedNumberOfRegistersToLoad = 8; // 0 - 7 INCLUSIVE
    char valueInEveryRegister = 1;
    byte[] bytesInMemory = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};


    when(machineMock.getRam().readBytes(any(), anyInt()))
        .thenReturn(bytesInMemory);


    DataRegister register = mock(DataRegister.class);
    when(register.get()).thenReturn(valueInEveryRegister);
    when(machineMock.getRegisters().getRegister(any()))
        .thenReturn(register);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);


    verify(register, times(expectedNumberOfRegistersToLoad)).set(anyChar());
  }

}
