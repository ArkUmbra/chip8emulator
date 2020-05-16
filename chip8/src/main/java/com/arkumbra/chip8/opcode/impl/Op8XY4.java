package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.DataRegisters;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op8XY4 implements OpCode {

  private static final char ZERO = (char)0;
  private static final char ONE = (char)1;

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op8XY4AddCarry;
  }

  @Override
  public void execute(char dataRegisters, Machine machine) {
    char dataRegisterXRaw = (char)(dataRegisters >> 8); // get left byte
    char dataRegisterYRaw = (char)((dataRegisters & 0x0F0) >> 4);

    DataRegisters registers = machine.getRegisters();
    DataRegister dataRegisterX = registers.getRegister(RegisterLabel.toKey(dataRegisterXRaw));
    DataRegister dataRegisterY = registers.getRegister(RegisterLabel.toKey(dataRegisterYRaw));
    DataRegister dataRegisterFCarryFlag = registers.getRegister(RegisterLabel.VF);

    char addResult = (char) (dataRegisterX.get() + dataRegisterY.get());
    char carryFlag = (addResult > 255) ? ONE : ZERO;
    dataRegisterFCarryFlag.set(carryFlag);

    // No unsigned byte in java, so mask the result up to 255
    dataRegisterX.set((char)(addResult & 0xFF));
  }

}
