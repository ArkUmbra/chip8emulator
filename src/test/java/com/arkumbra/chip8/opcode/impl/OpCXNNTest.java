package com.arkumbra.chip8.opcode.impl;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class OpCXNNTest {

  private OpCXNN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpCXNN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setVXToNNNWithBitwiseAndOnRandomNumber() {
    char inputOpCode = 0xC40F;

    DataRegister mockDataRegisterV4 = mock(DataRegister.class);
    when(machineMock.getRegisters().getRegister(RegisterLabel.V4))
        .thenReturn(mockDataRegisterV4);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute once
    sut.execute(opCodeData, machineMock);

    ArgumentCaptor<Character> capture1 = ArgumentCaptor.forClass(Character.class);
    verify(mockDataRegisterV4, times(1))
        .set(capture1.capture());

    // Execute twice
    sut.execute(opCodeData, machineMock);

    ArgumentCaptor<Character> capture2 = ArgumentCaptor.forClass(Character.class);
    verify(mockDataRegisterV4, times(2))
        .set(capture2.capture());

    assertNotEquals(capture1, capture2);
  }

}
