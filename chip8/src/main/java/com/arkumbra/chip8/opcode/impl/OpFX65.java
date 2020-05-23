package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.DataRegisters;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpFX65 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpFX65RegLoad;
  }


  @Override
  public void execute(char opData, Machine machine) {
    char regValueToLoadUpTo = (char)((opData & 0xF00) >> 8);

    byte[] loaded = machine.getRam().readBytes(
        machine.getIndexRegister(), regValueToLoadUpTo + 1);


    DataRegisters registers = machine.getRegisters();
    for (char i = 0; i <= regValueToLoadUpTo; i++) {
      DataRegister registerX = registers.getRegister(RegisterLabel.toKey(i));
      registerX.set((char)loaded[i]); //??
    }
  }

}