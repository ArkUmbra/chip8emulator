package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Key;
import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpEX9E implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpEX9EKeySkip;
  }

  @Override
  public void execute(char memoryAddress, Machine machine) {
//    char keyLabel = (char)((memoryAddress & 0xF00) >> 8);
//    Key key = machine.getKeys().getKey(KeyLabel.toKey(keyLabel));

    char registerRaw = (char)((memoryAddress & 0xF00) >> 8);
    DataRegister registerX = machine.getRegisters().getRegister(RegisterLabel.toKey(registerRaw));

    Key key = machine.getKeys().getKey(KeyLabel.toKey(registerX.get()));
    if (key.isPressed()) {
      machine.getProgramCounter().skipNextInstruction();
    }
  }

}
