package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Registers;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op9XY0 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op9XY0Skip;
  }

  @Override
  public void execute(char dataRegisters, Machine machine) {
    char dataRegisterXRaw = (char)(dataRegisters >> 8); // get left byte
    char dataRegisterYRaw = (char)((dataRegisters & 0x0F0) >> 4);

    Registers registers = machine.getRegisters();
    DataRegister dataRegisterX = registers.getRegister(RegisterLabel.toKey(dataRegisterXRaw));
    DataRegister dataRegisterY = registers.getRegister(RegisterLabel.toKey(dataRegisterYRaw));

    if (dataRegisterX.get() != dataRegisterY.get()) {
      machine.getProgramCounter().skipNextInstruction();
    }
  }

}
