package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public class DataRegistersImpl implements DataRegisters {

  private final Map<RegisterLabel, DataRegister> registers = new HashMap<>();

  public DataRegistersImpl() {
    for (RegisterLabel regLabel : RegisterLabel.values()) {
      DataRegister dataRegister = new DataRegisterImpl();
      this.registers.put(regLabel, dataRegister);
    }
  }

  @Override
  public DataRegister getRegister(RegisterLabel registerLabel) {
    return registers.get(registerLabel);
  }
}
