package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.FontLabel;
import com.arkumbra.chip8.machine.Machine;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class OpFX29Test {

  private OpFX29 sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpFX29();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  @Test
  public void testExecute_setIndexRegisterToLocationOfFontX() {
    char inputOpCode = 0xFC92;
    int addressOfFont = new Random().nextInt();

    when(machineMock.getFont().getAddress(FontLabel.FC))
        .thenReturn(addressOfFont);

    // Execute
    char opCodeData = sut.getBitMask().applyMask(inputOpCode);
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getProgramCounter(), times(1))
        .goTo(addressOfFont);
  }

}
