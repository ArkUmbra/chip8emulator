package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Registers;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op8XY5 implements OpCode {

  private static final char ZERO = (char)0;
  private static final char ONE = (char)1;

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op8XY5MinusBorrow;
  }

  @Override
  public void execute(char dataRegisters, Machine machine) {
    char dataRegisterXRaw = (char)(dataRegisters >> 8); // get left byte
    char dataRegisterYRaw = (char)((dataRegisters & 0x0F0) >> 4);

    Registers registers = machine.getRegisters();
    DataRegister dataRegisterX = registers.getRegister(RegisterLabel.toKey(dataRegisterXRaw));
    DataRegister dataRegisterY = registers.getRegister(RegisterLabel.toKey(dataRegisterYRaw));
    DataRegister dataRegisterFCarryFlag = registers.getRegister(RegisterLabel.VF);

    int minusResult = dataRegisterX.get() - dataRegisterY.get();
    char carryFlag = (minusResult < 0) ? ZERO : ONE;
    dataRegisterFCarryFlag.set(carryFlag);

    // No unsigned byte in java, so mask the result up to 256
    dataRegisterX.set((char)(minusResult & 0xFF));
  }

}
