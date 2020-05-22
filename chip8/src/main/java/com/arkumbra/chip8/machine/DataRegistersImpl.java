package com.arkumbra.chip8.machine;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DataRegistersImpl implements DataRegisters, SerializableData, Dumpable {
  // two bytes per register
  public static final int SERIALIZED_LENGTH = 16 * Character.BYTES;

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

  @Override
  public byte[] serialize() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(SERIALIZED_LENGTH);

    // must be in order
    RegisterLabel[] registerLabels = RegisterLabel.values();

    for (RegisterLabel registerLabel : registerLabels) {
      DataRegister register = registers.get(registerLabel);
      byteBuffer.putChar(register.get());
    }

    return byteBuffer.array();
  }

  @Override
  public void deserialize(byte[] data) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(data);

    // must be in order
    RegisterLabel[] registerLabels = RegisterLabel.values();

    for (RegisterLabel registerLabel : registerLabels) {
      DataRegister dataRegister = new DataRegisterImpl();
      dataRegister.set(byteBuffer.getChar());
      registers.put(registerLabel, dataRegister);
    }
  }
}
