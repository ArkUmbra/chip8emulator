package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public enum FontLabel {
  F0((char)0x0),
  F1((char)0x1),
  F2((char)0x2),
  F3((char)0x3),
  F4((char)0x4),
  F5((char)0x5),
  F6((char)0x6),
  F7((char)0x7),
  F8((char)0x8),
  F9((char)0x9),
  FA((char)0xA),
  FB((char)0xB),
  FC((char)0xC),
  FD((char)0xD),
  FE((char)0xE),
  FF((char)0xF);

  static Map<Character, FontLabel> cache = new HashMap<>();
  static {
    for (FontLabel label : FontLabel.values()) {
      cache.put(label.label, label);
    }
  }

  private char label;

  FontLabel(char label) {
    this.label = label;
  }

  public static FontLabel toKey(char label) {
    return cache.get(label);
  }
}
