package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public class DataRegistersImpl implements DataRegisters, Dumpable {

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

  @Override
  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("---- Registers ----");
    sb.append(System.lineSeparator());
    for (RegisterLabel label : RegisterLabel.values()) {
      sb.append(label.name())
          .append(" - ")
          .append(Integer.toHexString(registers.get(label).get()))
          .append("; ");
    }
    sb.append(System.lineSeparator());

    return sb.toString();
  }
}
