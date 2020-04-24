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

public class OpFX07Test {

  private OpFX07 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX07();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setRegisterXUsingCurrentValueOfDelayTimer() {
    char inputOpCode = 0xF107;
    char delayTimerValue = 0x07;

    Timer delayTimer = mock(Timer.class);
    when(delayTimer.getCurrentValue())
        .thenReturn(delayTimerValue);
    when(machineMock.getDelayTimer())
        .thenReturn(delayTimer);
    DataRegister register1 = mock(DataRegister.class);
    when(machineMock.getRegisters().getRegister(RegisterLabel.V1))
        .thenReturn(register1);


    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(register1, times(1))
        .set(delayTimerValue);
  }

}
