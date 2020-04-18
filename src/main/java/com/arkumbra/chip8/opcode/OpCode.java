package com.arkumbra.chip8.opcode;

import com.arkumbra.chip8.Machine;

public interface OpCode {

    OpCodeLabel getOpCodeLabel();

    void execute(Integer rawCommand, Machine machine);
}
