package com.arkumbra.chip8.opcode;

import com.arkumbra.chip8.opcode.impl.Op00E0;
import com.arkumbra.chip8.opcode.impl.Op00EE;
import com.arkumbra.chip8.opcode.impl.Op0NNN;
import com.arkumbra.chip8.opcode.impl.Op1NNN;
import com.arkumbra.chip8.opcode.impl.Op2NNN;
import com.arkumbra.chip8.opcode.impl.Op3XNN;
import com.arkumbra.chip8.opcode.impl.Op4XNN;
import com.arkumbra.chip8.opcode.impl.Op5XY0;
import com.arkumbra.chip8.opcode.impl.Op6XNN;
import com.arkumbra.chip8.opcode.impl.Op7XNN;
import com.arkumbra.chip8.opcode.impl.Op8XY0;
import com.arkumbra.chip8.opcode.impl.Op8XY1;
import com.arkumbra.chip8.opcode.impl.Op8XY2;
import com.arkumbra.chip8.opcode.impl.Op8XY3;
import com.arkumbra.chip8.opcode.impl.Op8XY4;
import com.arkumbra.chip8.opcode.impl.Op8XY5;
import com.arkumbra.chip8.opcode.impl.Op8XY6;
import com.arkumbra.chip8.opcode.impl.Op8XY7;
import com.arkumbra.chip8.opcode.impl.Op8XYE;
import com.arkumbra.chip8.opcode.impl.Op9XY0;
import com.arkumbra.chip8.opcode.impl.OpANNN;
import com.arkumbra.chip8.opcode.impl.OpBNNN;
import com.arkumbra.chip8.opcode.impl.OpCXNN;
import com.arkumbra.chip8.opcode.impl.OpDXYN;
import com.arkumbra.chip8.opcode.impl.OpEX9E;
import com.arkumbra.chip8.opcode.impl.OpEXA1;
import com.arkumbra.chip8.opcode.impl.OpFX07;
import com.arkumbra.chip8.opcode.impl.OpFX0A;
import com.arkumbra.chip8.opcode.impl.OpFX15;
import com.arkumbra.chip8.opcode.impl.OpFX18;
import com.arkumbra.chip8.opcode.impl.OpFX1E;
import com.arkumbra.chip8.opcode.impl.OpFX29;
import com.arkumbra.chip8.opcode.impl.OpFX33;
import com.arkumbra.chip8.opcode.impl.OpFX55;
import com.arkumbra.chip8.opcode.impl.OpFX65;

public class OpCodeLookupImpl implements OpCodeLookup {

  private OpCode op0NNN = new Op0NNN();
  private OpCode op00E0 = new Op00E0();
  private OpCode op00EE = new Op00EE();
  private OpCode op1NNN = new Op1NNN();
  private OpCode op2NNN = new Op2NNN();
  private OpCode op3XNN = new Op3XNN();
  private OpCode op4XNN = new Op4XNN();
  private OpCode op5XY0 = new Op5XY0();
  private OpCode op6XNN = new Op6XNN();
  private OpCode op7XNN = new Op7XNN();
  private OpCode op8XY0 = new Op8XY0();
  private OpCode op8XY1 = new Op8XY1();
  private OpCode op8XY2 = new Op8XY2();
  private OpCode op8XY3 = new Op8XY3();
  private OpCode op8XY4 = new Op8XY4();
  private OpCode op8XY5 = new Op8XY5();
  private OpCode op8XY6 = new Op8XY6();
  private OpCode op8XY7 = new Op8XY7();
  private OpCode op8XYE = new Op8XYE();
  private OpCode op9XY0 = new Op9XY0();
  private OpCode opANNN = new OpANNN();
  private OpCode opBNNN = new OpBNNN();
  private OpCode opCXNN = new OpCXNN();
  private OpCode opDXYN = new OpDXYN();
  private OpCode opEX9E = new OpEX9E();
  private OpCode opEXA1 = new OpEXA1();
  private OpCode opFX07 = new OpFX07();
  private OpCode opFX0A = new OpFX0A();
  private OpCode opFX15 = new OpFX15();
  private OpCode opFX18 = new OpFX18();
  private OpCode opFX1E = new OpFX1E();
  private OpCode opFX29 = new OpFX29();
  private OpCode opFX33 = new OpFX33();
  private OpCode opFX55 = new OpFX55();
  private OpCode opFX65 = new OpFX65();



  @Override
  public OpCode lookup(char rawOpCode) {
    OpCode foundOpCode = doLookup(rawOpCode);
    System.out.println("For rawOpCode " + Integer.toHexString(rawOpCode) + " found op " + foundOpCode.getOpCodeLabel());
    return foundOpCode;
  }

  private OpCode doLookup(char rawOpCode) {
    switch (rawOpCode & 0xF000) {
      case 0x0000:
        switch (rawOpCode & 0x00FF) {
          case 0x00E0:  return op00E0;
          case 0x00EE:  return op00EE;
          default:      return op0NNN;
        }
      case 0x1000: return op1NNN;
      case 0x2000: return op2NNN;
      case 0x3000: return op3XNN;
      case 0x4000: return op4XNN;
      case 0x5000: return op5XY0;
      case 0x6000: return op6XNN;
      case 0x7000: return op7XNN;
      case 0x8000:
        switch (rawOpCode & 0x000F) {
          case 0x0000: return op8XY0;
          case 0x0001: return op8XY1;
          case 0x0002: return op8XY2;
          case 0x0003: return op8XY3;
          case 0x0004: return op8XY4;
          case 0x0005: return op8XY5;
          case 0x0006: return op8XY6;
          case 0x0007: return op8XY7;
          case 0x000E: return op8XYE;
          default: throw new UnknownOpCodeException(rawOpCode);
        }
      case 0x9000: return op9XY0;
      case 0xA000: return opANNN;
      case 0xB000: return opBNNN;
      case 0xC000: return opCXNN;
      case 0xD000: return opDXYN;
      case 0xE000:
        switch (rawOpCode & 0x000F) {
          case 0x0001: return opEXA1;
          case 0x000E: return opEX9E;
          default: throw new UnknownOpCodeException(rawOpCode);
        }
      case 0xF000:
        switch (rawOpCode & 0xFF) {
          case 0x07: return opFX07;
          case 0x0A: return opFX0A;
          case 0x15: return opFX15;
          case 0x18: return opFX18;
          case 0x1E: return opFX1E;
          case 0x29: return opFX29;
          case 0x33: return opFX33;
          case 0x55: return opFX55;
          case 0x65: return opFX65;
          default: throw new UnknownOpCodeException(rawOpCode);
        }
      default: throw new UnknownOpCodeException(rawOpCode);
    }
  }
}

class UnknownOpCodeException extends RuntimeException {
  UnknownOpCodeException(char rawOpCode) {
    super("No OpCode implementation found for rawOpCode " + Integer.toHexString(rawOpCode));
  }
}
