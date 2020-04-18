package com.arkumbra.chip8.opcode;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.Machine;

public interface OpCode {

    BitMask getBitMask();

    OpCodeLabel getOpCodeLabel();

    void execute(char postMaskOpData, Machine machine);
}
