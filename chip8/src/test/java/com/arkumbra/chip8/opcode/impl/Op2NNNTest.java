package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.machine.Machine;
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
  public void testExecute() throws InterruptedException {
    char inputOpCode = 0xABCD;
    char expectedPushAddress = 0xBCD;


    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter(), times(1)).push(expectedPushAddress);
    verify(machineMock.getProgramCounter(), never()).pop();
    verify(machineMock.getRoutineRunner(), never()).runSingleCycle();
  }

}
