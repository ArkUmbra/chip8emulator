package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op8XYE implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op8XYEMostSigBitInF;
  }

  @Override
  public void execute(char opData, Machine machine) {
    char x = (char)((opData & 0xF00) >> 8);

    DataRegister registerX = machine.getRegisters().getRegister(RegisterLabel.toKey(x));
    DataRegister registerF = machine.getRegisters().getRegister(RegisterLabel.VF);

    registerF.set((char)(registerX.get() & 0x80));
    registerX.set((char)(registerX.get() << 1));
  }
}
