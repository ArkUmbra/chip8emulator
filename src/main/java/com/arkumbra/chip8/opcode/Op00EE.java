package com.arkumbra.chip8.opcode;

import com.arkumbra.chip8.ProgramCounter;
import com.arkumbra.chip8.screen.Screen;

public class Op00EE implements OpCode {
  private static final short opcode = 0x00EE;

  @Override
  public short getOpcode() {
    return opcode;
  }

  @Override
  public void execute(String rawCommand, Screen screen, ProgramCounter pg) {
    pg.pop();
  }
}
