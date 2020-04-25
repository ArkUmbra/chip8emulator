package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.FontLabel;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpFX29 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpFX29GoToFont;
  }

  @Override
  public void execute(char opData, Machine machine) {
    char fontCharacterXKeyRaw = (char)((opData & 0xF00) >> 8);

    int addressOfFontInMemory = machine.getFont().getAddress(FontLabel.toKey(fontCharacterXKeyRaw));

    machine.getProgramCounter().goTo(addressOfFontInMemory);
  }

}
