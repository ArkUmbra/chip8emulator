package com.arkumbra.chip8.opcode;

import com.arkumbra.chip8.ProgramCounter;
import com.arkumbra.chip8.screen.Screen;

public interface OpCode {

    short getOpcode();

    void execute(String rawCommand, Screen screen, ProgramCounter pg);
}
