package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public enum KeyLabel {
  K0((char)0x0),
  K1((char)0x1),
  K2((char)0x2),
  K3((char)0x3),
  K4((char)0x4),
  K5((char)0x5),
  K6((char)0x6),
  K7((char)0x7),
  K8((char)0x8),
  K9((char)0x9),
  KA((char)0xA),
  KB((char)0xB),
  KC((char)0xC),
  KD((char)0xD),
  KE((char)0xE),
  KF((char)0xF);

  static Map<Character, KeyLabel> cache = new HashMap<>();
  static {
    for (KeyLabel label : KeyLabel.values()) {
      cache.put(label.label, label);
    }
  }

  private char label;

  KeyLabel(char label) {
    this.label = label;
  }

  public char getKey() {
    return label;
  }

  public static KeyLabel toKey(char label) {
    return cache.get(label);
  }

}
