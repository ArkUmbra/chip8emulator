package com.arkumbra.chip8.machine;

import com.arkumbra.chip8.opcode.OpCodeLabel;

public interface RoutineRunner {

  OpCodeLabel runSingleCycle() throws InterruptedException;

}
