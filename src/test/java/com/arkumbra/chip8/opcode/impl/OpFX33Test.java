package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.FontLabel;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class OpFX33Test {

  private OpFX33 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX33();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setIndexRegisterToLocationOfFontX() {
    char inputOpCode = 0xFA33;
    char valueInRegister = 123;

    DataRegister registerX = mock(DataRegister.class);
    when(registerX.get()).thenReturn(valueInRegister);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VA))
        .thenReturn(registerX);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);


    verify(machineMock.getMemory(), times(1))
        .write(machineMock.getIndexRegister().get(), new byte[]{1, 2, 3});
  }

}
