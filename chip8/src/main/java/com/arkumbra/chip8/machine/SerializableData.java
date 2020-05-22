package com.arkumbra.chip8.machine;

public interface SerializableData {
  byte[] serialize();
  void deserialize(byte[] data);

}
