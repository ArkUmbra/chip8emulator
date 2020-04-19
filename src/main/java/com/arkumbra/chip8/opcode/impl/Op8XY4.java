package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.DataRegister;
import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.RegisterKey;
import com.arkumbra.chip8.Registers;
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

    Registers registers = machine.getRegisters();
    DataRegister dataRegisterX = registers.getRegister(RegisterKey.toKey(dataRegisterXRaw));
    DataRegister dataRegisterY = registers.getRegister(RegisterKey.toKey(dataRegisterYRaw));
    DataRegister dataRegisterFCarryFlag = registers.getRegister(RegisterKey.VF);

    char addResult = (char) (dataRegisterX.get() + dataRegisterY.get());
    char carryFlag = (addResult > 255) ? ONE : ZERO;
    dataRegisterFCarryFlag.set(carryFlag);

    // No unsigned byte in java, so mask the result up to 256
    dataRegisterX.set((char)(addResult & 0xFF));
  }

}
