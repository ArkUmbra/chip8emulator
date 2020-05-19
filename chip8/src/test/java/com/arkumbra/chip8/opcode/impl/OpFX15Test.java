package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Timer;
import org.junit.Before;
import org.junit.Test;

public class OpFX15Test {

  private OpFX15 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX15();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setDelayTimerToValueOfRegisterX() {
    char inputOpCode = 0xFE15;
    char currentRegisterValue = 0xF001;

    DataRegister dataRegisterE = mock(DataRegister.class);
    when(dataRegisterE.get())
        .thenReturn(currentRegisterValue);
    when(machineMock.getRegisters().getRegister(RegisterLabel.VE))
        .thenReturn(dataRegisterE);
    Timer delayTimer = mock(Timer.class);
    when(machineMock.getDelayTimer())
        .thenReturn(delayTimer);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(delayTimer, times(1))
        .set(currentRegisterValue);
  }

}
