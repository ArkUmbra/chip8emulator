package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.Key;
import com.arkumbra.chip8.machine.KeyLabel;
import com.arkumbra.chip8.machine.Machine;
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
    char keyLabel = (char)((memoryAddress & 0xF00) >> 8);

    Key key = machine.getKeys().getKey(KeyLabel.toKey(keyLabel));
    if (key.isPressed()) {
      machine.getProgramCounter().skipNextInstruction();
    }
  }

}
