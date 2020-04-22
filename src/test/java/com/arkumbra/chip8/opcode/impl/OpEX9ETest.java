package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.Key;
import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.Machine;
import org.junit.Before;
import org.junit.Test;

public class OpEX9ETest {

  private OpEX9E sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpEX9E();
    this.machineMock = TestUtils.mockMachineAndParts();
  }


  @Test
  public void testExecute_skipNextInstructionWhenKeyXIsPressed() {
    char inputOpCode = 0xEA9E;
    boolean isPressed = true;

    Key key = mock(Key.class);
    when(key.isPressed())
        .thenReturn(isPressed);
    when(machineMock.getKeys().getKey(KeyLabel.KA))
        .thenReturn(key);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter(), times(1))
        .skipNextInstruction();
  }

  @Test
  public void testExecute_dontSkipNextInstructionWhenKeyXIsNotPressed() {
    char inputOpCode = 0xEA9E;
    boolean isPressed = false;

    Key key = mock(Key.class);
    when(key.isPressed())
        .thenReturn(isPressed);
    when(machineMock.getKeys().getKey(KeyLabel.KA))
        .thenReturn(key);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter(), never())
        .skipNextInstruction();
  }

}
