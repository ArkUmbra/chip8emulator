package com.arkumbra.chip8.opcode.impl;

import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkumbra.chip8.TestUtils;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.DataRegisters;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Screen;
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
    char inputOpCode = 0xD451; // just draw one row of pixels, using coordinates in registers 4 & 5
    byte memoryContentAtI = 0b01010101;
    int currentMemoryLocation = 123;

    char xCoordinate = 59;
    char yCoordinate = 31;
    boolean pixelsUnsetByDraw = true;

    DataRegisters registers = machineMock.getRegisters();
    DataRegister xReg = mock(DataRegister.class);
    when(xReg.get()).thenReturn(xCoordinate);
    DataRegister yReg = mock(DataRegister.class);
    when(yReg.get()).thenReturn(yCoordinate);
    DataRegister fReg = mock(DataRegister.class);
    when(registers.getRegister(RegisterLabel.V4)).thenReturn(xReg);
    when(registers.getRegister(RegisterLabel.V5)).thenReturn(yReg);
    when(registers.getRegister(RegisterLabel.VF)).thenReturn(fReg);

    when(machineMock.getProgramCounter().getPosition())
        .thenReturn(currentMemoryLocation);
    when(machineMock.getMemory().readBytes(machineMock.getIndexRegister(), 1))
        .thenReturn(new byte[]{memoryContentAtI});

    Screen screen = machineMock.getScreen();
    when(screen.draw(anyByte(), anyInt(), anyInt())).thenReturn(pixelsUnsetByDraw);

    char opCodeData = sut.getBitMask().applyMask(inputOpCode);

    // Execute once
    sut.execute(opCodeData, machineMock);

    verify(machineMock.getScreen(), times(1))
        .draw(memoryContentAtI, xCoordinate, yCoordinate);
    verify(fReg, times(1)).set((char)1);
  }

}
