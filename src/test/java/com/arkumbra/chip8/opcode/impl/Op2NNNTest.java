package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import org.junit.Before;
import org.junit.Test;

public class Op2NNNTest {

  private Op2NNN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new Op2NNN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute() {
    char inputOpCode = 0xABCD;
    char expectedPushAddress = 0xBCD;

    when(machineMock.getRoutineRunner().runCycle())
        .thenReturn(OpCodeLabel.Op00EEReturn);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter(), times(1)).push(expectedPushAddress);
    verify(machineMock.getProgramCounter(), times(1)).pop();
    verify(machineMock.getRoutineRunner(), times(1)).runCycle();
  }

}
