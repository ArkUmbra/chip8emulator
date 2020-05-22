package com.arkumbra.chip8.machine;

public interface Timer extends TickUpdateable, SerializableData {

  char getCurrentValue();
  void set(char value);

}
