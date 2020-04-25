package com.arkumbra.chip8.machine;

import java.util.HashMap;
import java.util.Map;

public class FontImpl implements Font {
  private final Map<FontLabel, FontData> fontCharacters = new HashMap<>();

  /*
  0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
  0x20, 0x60, 0x20, 0x20, 0x70, // 1
  0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
  0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
  0x90, 0x90, 0xF0, 0x10, 0x10, // 4
  0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
  0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
  0xF0, 0x10, 0x20, 0x40, 0x40, // 7
  0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
  0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
  0xF0, 0x90, 0xF0, 0x90, 0x90, // A
  0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
  0xF0, 0x80, 0x80, 0x80, 0xF0, // C
  0xE0, 0x90, 0x90, 0x90, 0xE0, // D
  0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
  0xF0, 0x80, 0xF0, 0x80, 0x80  // F
   */

  private static final byte[] F0 = byteArray(0xF0, 0x90, 0x90, 0x90, 0xF0);
  private static final byte[] F1 = byteArray(0x20, 0x60, 0x20, 0x20, 0x70);
  private static final byte[] F2 = byteArray(0xF0, 0x10, 0xF0, 0x80, 0xF0);
  private static final byte[] F3 = byteArray(0xF0, 0x10, 0xF0, 0x10, 0xF0);
  private static final byte[] F4 = byteArray(0x90, 0x90, 0xF0, 0x10, 0x10);
  private static final byte[] F5 = byteArray(0xF0, 0x80, 0xF0, 0x10, 0xF0);
  private static final byte[] F6 = byteArray(0xF0, 0x80, 0xF0, 0x90, 0xF0);
  private static final byte[] F7 = byteArray(0xF0, 0x10, 0x20, 0x40, 0x40);
  private static final byte[] F8 = byteArray(0xF0, 0x90, 0xF0, 0x90, 0xF0);
  private static final byte[] F9 = byteArray(0xF0, 0x90, 0xF0, 0x10, 0xF0);
  private static final byte[] FA = byteArray(0xF0, 0x90, 0xF0, 0x90, 0x90);
  private static final byte[] FB = byteArray(0xE0, 0x90, 0xE0, 0x90, 0xE0);
  private static final byte[] FC = byteArray(0xF0, 0x80, 0x80, 0x80, 0xF0);
  private static final byte[] FD = byteArray(0xE0, 0x90, 0x90, 0x90, 0xE0);
  private static final byte[] FE = byteArray(0xF0, 0x80, 0xF0, 0x80, 0xF0);
  private static final byte[] FF = byteArray(0xF0, 0x80, 0xF0, 0x80, 0x80);


  private static byte[] byteArray(int b1, int b2, int b3, int b4, int b5) {
    return new byte[]{(byte)b1, (byte)b2, (byte)b3, (byte)b4, (byte)b5};
  }

  public FontImpl() {
    fontCharacters.put(FontLabel.F0, new FontData(F0));
    fontCharacters.put(FontLabel.F1, new FontData(F1));
    fontCharacters.put(FontLabel.F2, new FontData(F2));
    fontCharacters.put(FontLabel.F3, new FontData(F3));
    fontCharacters.put(FontLabel.F4, new FontData(F4));
    fontCharacters.put(FontLabel.F5, new FontData(F5));
    fontCharacters.put(FontLabel.F6, new FontData(F6));
    fontCharacters.put(FontLabel.F7, new FontData(F7));
    fontCharacters.put(FontLabel.F8, new FontData(F8));
    fontCharacters.put(FontLabel.F9, new FontData(F9));
    fontCharacters.put(FontLabel.FA, new FontData(FA));
    fontCharacters.put(FontLabel.FB, new FontData(FB));
    fontCharacters.put(FontLabel.FC, new FontData(FC));
    fontCharacters.put(FontLabel.FD, new FontData(FD));
    fontCharacters.put(FontLabel.FE, new FontData(FE));
    fontCharacters.put(FontLabel.FF, new FontData(FF));
  }

  @Override
  public byte[] getFontDataAndSetFixedAddressLocation(int addressLocation, FontLabel fontLabel) {
    FontData fontData = fontCharacters.get(fontLabel);
    fontData.fixedAddressInMemory = addressLocation;
    return fontData.bytes;
  }

  @Override
  public int getAddress(FontLabel fontLabel) {
    // This is only going to work after the above has been set
    return fontCharacters.get(fontLabel).fixedAddressInMemory;
  }
}

class FontData {
  byte[] bytes;
  int fixedAddressInMemory;

  FontData(byte[] bytes) {
    this.bytes = bytes;
  }
}
