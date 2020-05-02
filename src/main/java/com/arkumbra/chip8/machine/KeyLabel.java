package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public enum KeyLabel {
  K0((char)0x0, "0"),
  K1((char)0x1, "1"),
  K2((char)0x2, "2"),
  K3((char)0x3, "3"),
  K4((char)0x4, "4"),
  K5((char)0x5, "5"),
  K6((char)0x6, "6"),
  K7((char)0x7, "7"),
  K8((char)0x8, "8"),
  K9((char)0x9, "9"),
  KA((char)0xA, "A"),
  KB((char)0xB, "B"),
  KC((char)0xC, "C"),
  KD((char)0xD, "D"),
  KE((char)0xE, "E"),
  KF((char)0xF, "F");

  static Map<Character, KeyLabel> cache = new HashMap<>();
  static {
    for (KeyLabel label : KeyLabel.values()) {
      cache.put(label.label, label);
    }
  }

  private final char label;
  private final String keyName;

  KeyLabel(char label, String keyName) {
    this.label = label;
    this.keyName = keyName;
  }

  public char getKey() {
    return label;
  }

  public String getKeyName() {
    return keyName;
  }

  public static KeyLabel toKey(char label) {
    return cache.get(label);
  }

}
