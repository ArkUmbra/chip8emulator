package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.Machine;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class Op0NNN implements OpCode {

    @Override
    public OpCodeLabel getOpCodeLabel() {
        return OpCodeLabel.Op0NNNCallRCA1802;
    }

    @Override
    public void execute(Integer rawCommand, Machine machine) {

    }
}