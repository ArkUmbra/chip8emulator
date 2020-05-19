package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Logger;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import org.apache.commons.codec.binary.BinaryCodec;

public class OpDXYN implements OpCode {
  private Logger logger = new Logger(getClass());
  private static final char ONE = (char)1;
  private static final char ZERO = (char)0;


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
    char xRegisterRaw = (char)((memoryAddress & 0xF00) >> 8);
    char yRegisterRaw = (char)((memoryAddress & 0xF0) >> 4);
    char nRows = (char)(memoryAddress & 0xF); // rows of 1 byte (8 bits of pixel flags);

    DataRegister registerX = machine.getRegisters().getRegister(RegisterLabel.toKey(xRegisterRaw));
    DataRegister registerY = machine.getRegisters().getRegister(RegisterLabel.toKey(yRegisterRaw));


    byte[] bytesOfPixels = machine.getMemory().readBytes(machine.getIndexRegister(), nRows);

    if (nRows != bytesOfPixels.length) {
      throw new RuntimeException("Size mismatch. Requsted " + nRows + ", but got " + bytesOfPixels.length);
    }



    boolean pixelsFlipped = false;
    for (int rowOffset = 0; rowOffset < bytesOfPixels.length; rowOffset++) {
      int x = registerX.get();
      int y = registerY.get() + rowOffset;

      logger.debug("Requesting draw " + BinaryCodec.toAsciiString(new byte[]{bytesOfPixels[rowOffset]})
          + " at location [" + x + ", " + y + "]");

      boolean unset = machine.getScreen().draw(bytesOfPixels[rowOffset], x, y);

      pixelsFlipped = pixelsFlipped || unset;
    }

    machine.getRegisters().getRegister(RegisterLabel.VF).set(
        pixelsFlipped ? ONE : ZERO
    );
  }

}
