package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.DataRegisters;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpFX55 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpFX55RegDump;
  }


  @Override
  public void execute(char opData, Machine machine) {
    char regValueToDumpUpTo = (char)((opData & 0xF00) >> 8);

    DataRegisters registers = machine.getRegisters();
    byte[] dumped = new byte[regValueToDumpUpTo + 1];
    for (char i = 0; i <= regValueToDumpUpTo; i++) {
      DataRegister registerX = registers.getRegister(RegisterLabel.toKey(i));
      dumped[i] = (byte)registerX.get(); //??
    }

    machine.getRam().write(machine.getIndexRegister().get(), dumped);
  }

}
