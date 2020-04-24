package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Timer;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpFX15 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpFX15SetDelayTimer;
  }

  @Override
  public void execute(char opData, Machine machine) {
    char registerXKeyRaw = (char)((opData & 0xF00) >> 8);

    DataRegister registerX = machine.getRegisters().getRegister(RegisterLabel.toKey(registerXKeyRaw));

    Timer delayTimer = machine.getDelayTimer();
    delayTimer.set(registerX.get());
  }

}
