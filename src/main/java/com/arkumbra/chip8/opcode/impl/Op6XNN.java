package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.DataRegister;
import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.RadixUtils;
import com.arkumbra.chip8.RegisterKey;
import com.arkumbra.chip8.Registers;
import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op6XNN implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.Op6XNNSet;
  }

  @Override
  public void execute(char dataRegisterAndData, Machine machine) {
    char dataRegisterRaw = (char)(dataRegisterAndData >> 8); // get left byte
    char dataRaw = (char)(dataRegisterAndData & 0x0FF); // get right byte

    Registers registers = machine.getRegisters();
    DataRegister dataRegister = registers.getRegister(RegisterKey.toKey(dataRegisterRaw));
    dataRegister.set(dataRaw);
  }

}
