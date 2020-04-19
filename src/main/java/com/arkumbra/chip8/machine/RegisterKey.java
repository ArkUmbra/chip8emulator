package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public enum RegisterKey {
  V0((char)0x0),
  V1((char)0x1),
  V2((char)0x2),
  V3((char)0x3),
  V4((char)0x4),
  V5((char)0x5),
  V6((char)0x6),
  V7((char)0x7),
  V8((char)0x8),
  V9((char)0x9),
  VA((char)0xA),
  VB((char)0xB),
  VC((char)0xC),
  VD((char)0xD),
  VE((char)0xE),
  VF((char)0xF);

  static Map<Character, RegisterKey> cache = new HashMap<>();
  static {
    for (RegisterKey key : RegisterKey.values()) {
      cache.put(key.key, key);
    }
  }

  private char key;

  RegisterKey(char key) {
    this.key = key;
  }

  public static RegisterKey toKey(char val) {
    return cache.get(val);
  }
}
