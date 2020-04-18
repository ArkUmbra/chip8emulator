package com.arkumbra.chip8;

import com.arkumbra.chip8.opcode.OpCodeLabel;

public interface RoutineRunner {

  OpCodeLabel runCycle();

}
