package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Key;
import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import org.junit.Before;
import org.junit.Test;

public class OpFX0ATest {

  private OpFX0A sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX0A();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_waitForAnyKeyPressAndStoreInRegisterX() throws InterruptedException {
    char inputOpCode = 0xF20A;
    char expectedKeyLabel = 0x01;

    DataRegister register2 = mock(DataRegister.class);
    when(machineMock.getRegisters().getRegister(RegisterLabel.V2))
        .thenReturn(register2);
    when(machineMock.getKeys().waitForNextKeyPress())
        .thenReturn(KeyLabel.toKey(expectedKeyLabel));


    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute
    sut.execute(opCodeData, machineMock);

    verify(register2, times(1))
        .set(expectedKeyLabel);
  }

}
