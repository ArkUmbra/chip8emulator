package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;
import java.util.Random;

public class OpCXNN implements OpCode {

  private final Random random;

  public OpCXNN() {
    this.random = new Random();
  }

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpCXNNRand;
  }

  @Override
  public void execute(char memoryAddress, Machine machine) {
    char registerXRaw = (char)((memoryAddress & 0xF00) >> 8);
    char valueNNRaw = (char)(memoryAddress & 0xFF);

    DataRegister vXRegister = machine.getRegisters().getRegister(RegisterLabel.toKey(registerXRaw));

    char randomValue = (char)random.nextInt(255);
    int newSetValue = (char)(randomValue & valueNNRaw);

    vXRegister.set((char)(newSetValue & 0xFFFF));
  }

}
