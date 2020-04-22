package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpDXYN implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpDXYNDraw;
  }

  @Override
  public void execute(char memoryAddress, Machine machine) {
    char xPos = (char)((memoryAddress & 0xF00) >> 8);
    char yPos = (char)((memoryAddress & 0xF0) >> 4);
    char nRows = (char)(memoryAddress & 0xF); // rows of 1 byte (8 bits of pixel flags)


    byte[] bytesOfPixels = machine.getMemory().readBytes(machine.getProgramCounter(), nRows);

    for (byte pixelFlags : bytesOfPixels) {
      machine.getScreen().draw(pixelFlags, xPos, yPos);
    }
  }

}
