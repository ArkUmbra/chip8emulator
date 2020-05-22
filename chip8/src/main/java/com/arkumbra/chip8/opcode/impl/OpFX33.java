package com.arkumbra.chip8.opcode.impl;

import com.arkumbra.chip8.bitmask.BitMask;
import com.arkumbra.chip8.bitmask.BitMasks;
import com.arkumbra.chip8.machine.DataRegister;
import com.arkumbra.chip8.machine.Machine;
import com.arkumbra.chip8.machine.RegisterLabel;
import com.arkumbra.chip8.opcode.OpCode;
import com.arkumbra.chip8.opcode.OpCodeLabel;

public class OpFX33 implements OpCode {

  @Override
  public BitMask getBitMask() {
    return BitMasks.THREE_COL;
  }

  @Override
  public OpCodeLabel getOpCodeLabel() {
    return OpCodeLabel.OpFX33BCD;
  }

  /**
   * From Wikipedia https://en.wikipedia.org/wiki/CHIP-8#Opcode_table
   *
   * Stores the binary-coded decimal representation of VX, with the most significant of three digits
   * at the address in I, the middle digit at I plus 1, and the least significant digit at I plus 2.
   * (In other words, take the decimal representation of VX, place the hundreds digit in memory at
   * location in I, the tens digit at location I+1, and the ones digit at location I+2.)
   */
  @Override
  public void execute(char opData, Machine machine) {
    char registerXKeyRaw = (char)((opData & 0xF00) >> 8);

    RegisterLabel registerLabel = RegisterLabel.toKey(registerXKeyRaw);
    DataRegister registerX = machine.getRegisters().getRegister(registerLabel);

    char regValue = registerX.get();

    /*
     * Couldn't figure out how to implement this one, so solution pilfered from TJA here
     * http://www.multigesture.net/wp-content/uploads/mirror/goldroad/chip8.shtml
     */
    byte hundreds = (byte)(regValue / 100);
    byte tens = (byte)((regValue / 10) % 10);
    byte ones = (byte)((regValue % 100) % 10);

    machine.getMemory().write(machine.getIndexRegister().get(), new byte[]{hundreds, tens, ones});
  }

}
