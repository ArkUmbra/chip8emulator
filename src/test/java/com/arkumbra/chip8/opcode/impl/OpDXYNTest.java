package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.Machine;
import org.junit.Before;
import org.junit.Test;

public class OpDXYNTest {

  private OpDXYN sut;
  private Machine machineMock;

  @Before
  public void setup() {
    this.sut = new OpDXYN();
    this.machineMock = TestUtils.mockMachineAndParts();
  }

  /**
   * Draws a sprite at coordinate (VX, VY) that has a width of 8 pixels and a height of N pixels.
   * Each row of 8 pixels is read as bit-coded starting from memory location I; I value doesn’t
   * change after the execution of this instruction. As described above, VF is set to 1 if any
   * screen pixels are flipped from set to unset when the sprite is drawn, and to 0 if that
   * doesn’t happen
   */
  @Test
  public void testExecute_drawSpriteAtXYWidth8AndHeightN() {
    char inputOpCode = 0xD451; // just draw one row of pixels at 4,5
    byte memoryContentAtI = 0b01010101;
    int currentMemoryLocation = 123;

    when(machineMock.getProgramCounter().getPosition())
        .thenReturn(currentMemoryLocation);
    when(machineMock.getMemory().readBytes(machineMock.getIndexRegister(), 1))
        .thenReturn(new byte[]{memoryContentAtI});

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute once
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getScreen(), times(1))
        .draw(memoryContentAtI, 4, 5);
  }

  @Test
  public void testExecute_drawSpriteAtXYWidth8AndHeightNForHeightAbove1() {
    char inputOpCode = 0xDAF2; // just draw one row of pixels at 4,5
    byte memoryContentAtIRow1 = 0b01010101;
    byte memoryContentAtIRow2 = 0b00101010;
    int currentMemoryLocation = 123;

    when(machineMock.getProgramCounter().getPosition())
        .thenReturn(currentMemoryLocation);
    when(machineMock.getMemory().readBytes(machineMock.getIndexRegister(), 2))
        .thenReturn(new byte[]{memoryContentAtIRow1, memoryContentAtIRow2});

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute once
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getScreen(), times(1))
        .draw(memoryContentAtIRow1, 0xA, 0xF);
    verify(machineMock.getScreen(), times(1))
        .draw(memoryContentAtIRow2, 0xA, 0xF);
  }

}
