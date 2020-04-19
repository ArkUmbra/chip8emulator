package com.arkumbra.chip8.opcode;

public enum OpCodeLabel {
  Op0NNNCallRCA1802,
  OpOOEOClearScreen,
  Op00EEReturn,
  Op1NNNGoto,
  Op2NNNCallSubroutine,
  Op3XNNSkip,
  Op4XNNSkip,
  Op5XY0Skip,
  Op6XNNSet,
  Op7XNNAdd,
  Op8XY0CopyRegister,
  Op8XY1BitwiseOr,
  Op8XY2BitwiseAnd, Op8XY3BitwiseXOR, Op8XY4AddCarry,

}
