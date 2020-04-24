package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.machine.Timer;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpFX18 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpFX18SetSoundTimer;
  }

  @Override
  public void execute(char opData, Machine machine) {
    char registerXKeyRaw = (char)((opData & 0xF00) >> 8);

    DataRegister registerX = machine.getRegisters().getRegister(RegisterLabel.toKey(registerXKeyRaw));

    Timer soundTimer = machine.getSoundTimer();
    soundTimer.set(registerX.get());
  }

}
